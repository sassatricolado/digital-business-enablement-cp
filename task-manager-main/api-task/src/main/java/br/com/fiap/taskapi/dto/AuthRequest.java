package br.com.fiap.taskapi.dto;

import lombok.Data;

public record AuthRequest (
        String username,
        String password
){}
