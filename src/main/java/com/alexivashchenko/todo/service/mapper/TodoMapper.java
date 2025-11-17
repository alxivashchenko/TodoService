package com.alexivashchenko.todo.service.mapper;

import com.alexivashchenko.todo.service.dto.TodoRequestDto;
import com.alexivashchenko.todo.service.dto.TodoResponseDto;
import com.alexivashchenko.todo.service.model.Todo;
import org.springframework.stereotype.Component;

@Component
public class TodoMapper {

    public Todo toEntity(TodoRequestDto request) {
        return Todo.builder()
                .title(request.title())
                .description(request.description())
                .status(request.status())
                .userId(request.userId())
                .build();
    }

    public TodoResponseDto toResponseDto(Todo todo) {
        return new TodoResponseDto(
                todo.getId(),
                todo.getTitle(),
                todo.getDescription(),
                todo.getStatus(),
                todo.getUserId()
        );
    }

}
