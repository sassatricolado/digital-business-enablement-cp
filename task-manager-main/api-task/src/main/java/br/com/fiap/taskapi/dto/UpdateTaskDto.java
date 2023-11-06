package br.com.fiap.taskapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record UpdateTaskDto (
        @NotNull
        Long id,
        String title,
        String description,
        Date date,
        Boolean status
){
}
