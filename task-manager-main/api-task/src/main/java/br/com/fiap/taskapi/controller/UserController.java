package br.com.fiap.taskapi.controller;

import br.com.fiap.taskapi.dto.AuthRequest;
import br.com.fiap.taskapi.dto.UserCreatedDto;
import br.com.fiap.taskapi.dto.UserInfoDto;
import br.com.fiap.taskapi.model.UserInfo;
import br.com.fiap.taskapi.service.JwtService;
import br.com.fiap.taskapi.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
public class UserController {

    @Autowired
    private UserInfoService service;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authManager;

    @GetMapping
    @RequestMapping("/users")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Page<UserCreatedDto>> listAllUsers (Pageable pageable) {
        return ResponseEntity.ok(service.listAll(pageable));
    }

    @PostMapping("/signup")
    public ResponseEntity signUp (@RequestBody UserInfoDto data, UriComponentsBuilder uriBuilder) {
        UserInfo user = new UserInfo(data);
        service.addUser(user);

        var uri = uriBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(new UserCreatedDto(user));
    }

    @PostMapping("/signin")
    public String authenticateAndGetToken (@RequestBody AuthRequest authRequest) {
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.username());
        } else {
            throw new UsernameNotFoundException("Error");
        }
    }
}