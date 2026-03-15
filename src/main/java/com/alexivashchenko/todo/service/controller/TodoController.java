package com.alexivashchenko.todo.service.controller;

import com.alexivashchenko.todo.service.dto.TodoRequestDto;
import com.alexivashchenko.todo.service.dto.TodoResponseDto;
import com.alexivashchenko.todo.service.exception.UnauthorizedException;
import com.alexivashchenko.todo.service.service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    private UUID parseUserId(String userIdHeader) {
        if (userIdHeader == null || userIdHeader.isBlank()) {
            throw new UnauthorizedException("Missing user identity");
        }
        try {
            return UUID.fromString(userIdHeader);
        } catch (IllegalArgumentException e) {
            throw new UnauthorizedException("Invalid user identity format");
        }
    }

    @GetMapping
    public ResponseEntity<List<TodoResponseDto>> getAllTodos(
            @RequestHeader("X-User-Id") String userIdHeader) {

        UUID userId = parseUserId(userIdHeader);
        return ResponseEntity.ok(todoService.getAllTodosByUserId(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoResponseDto> getTodoById(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") String userIdHeader) {

        UUID userId = parseUserId(userIdHeader);
        return ResponseEntity.ok(todoService.getTodoById(id, userId));
    }

    @PostMapping
    public ResponseEntity<TodoResponseDto> createTodo(
            @Valid @RequestBody TodoRequestDto request,
            @RequestHeader("X-User-Id") String userIdHeader) {

        UUID userId = parseUserId(userIdHeader);
        return ResponseEntity.ok(todoService.createTodo(request, userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoResponseDto> updateTodo(
            @PathVariable Long id,
            @Valid @RequestBody TodoRequestDto request,
            @RequestHeader("X-User-Id") String userIdHeader) {

        UUID userId = parseUserId(userIdHeader);
        return ResponseEntity.ok(todoService.updateTodo(id, request, userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") String userIdHeader) {

        UUID userId = parseUserId(userIdHeader);
        todoService.deleteTodo(id, userId);
        return ResponseEntity.noContent().build();
    }

}
