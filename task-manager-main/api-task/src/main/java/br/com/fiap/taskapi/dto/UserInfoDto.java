package br.com.fiap.apitask.dto;

import br.com.fiap.apitask.model.UserRole;

public record UserInfoDto(
        String username,
        String password,
        UserRole roles
) {
}
