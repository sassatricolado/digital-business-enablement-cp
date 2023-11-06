package br.com.fiap.apitask.service;

import br.com.fiap.apitask.dto.UserCreatedDto;
import br.com.fiap.apitask.model.UserInfo;
import br.com.fiap.apitask.repository.UserInformationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {

    @Autowired
    private UserInformationRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userInfo = repository.findByUsername(username);
        return userInfo
                .orElseThrow(() -> new UsernameNotFoundException("404 - O usuário não foi encontrado"));
    }

    public void addUser (UserInfo userInfo) {
        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        repository.save(userInfo);
    }

    public Page<UserCreatedDto> listAll(Pageable pageable) {
        return repository.findAll(pageable).map(UserCreatedDto::new);
    }
}
