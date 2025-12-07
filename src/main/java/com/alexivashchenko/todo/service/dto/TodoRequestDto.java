package com.alexivashchenko.todo.service.dto;

import com.alexivashchenko.todo.service.model.Status;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record TodoRequestDto(
        @NotBlank String title,
        @NotBlank String description,
        Status status
//        @Min(1) Long userId
) {
}
