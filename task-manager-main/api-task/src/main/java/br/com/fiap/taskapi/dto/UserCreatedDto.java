package br.com.fiap.taskapi.dto;

import br.com.fiap.taskapi.model.UserInfo;
import br.com.fiap.taskapi.model.UserRole;

public record UserCreatedDto (Long id, String username, UserRole roles){

    public UserCreatedDto (UserInfo user) {
        this (user.getId(), user.getUsername(), user.getRole());
    }
}
