package com.alexivashchenko.todo.service.dto;

import com.alexivashchenko.todo.service.model.Status;

import java.time.LocalDateTime;

public record TodoResponseDto(
        Long id,
        String title,
        String description,
        Status status
//        String userId
) {
}
