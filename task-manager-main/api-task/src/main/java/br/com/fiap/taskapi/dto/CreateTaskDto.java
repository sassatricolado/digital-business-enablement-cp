package br.com.fiap.taskapi.dto;

import java.util.Date;

public record CreateTaskDto(
        String title,
        String description,
        Date date
) {
}
