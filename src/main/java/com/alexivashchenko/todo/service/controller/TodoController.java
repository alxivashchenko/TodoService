package com.alexivashchenko.todo.service.controller;

import com.alexivashchenko.todo.service.dto.TodoRequestDto;
import com.alexivashchenko.todo.service.dto.TodoResponseDto;
import com.alexivashchenko.todo.service.exception.UnauthorizedException;
import com.alexivashchenko.todo.service.service.TodoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    private String getUserIdFromHeader(HttpServletRequest request) {
        String userId = request.getHeader("X-User-Id");
        if (userId == null)
            throw new UnauthorizedException("Missing user identity");
        return userId;
    }

    @GetMapping
    public ResponseEntity<List<TodoResponseDto>> getAllTodos(HttpServletRequest req) {
        return ResponseEntity.ok(todoService.getAllTodosByUserId(getUserIdFromHeader(req)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoResponseDto> getTodoById(@PathVariable Long id, HttpServletRequest req) {
        return ResponseEntity.ok(todoService.getTodoById(id, getUserIdFromHeader(req)));
    }

    @PostMapping
    public ResponseEntity<TodoResponseDto> createTodo(@Valid @RequestBody TodoRequestDto request,
                                                      HttpServletRequest req) {
        return ResponseEntity.ok(todoService.createTodo(request, getUserIdFromHeader(req)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoResponseDto> updateTodo(@PathVariable Long id,
                                                      @Valid @RequestBody TodoRequestDto request,
                                                      HttpServletRequest req) {
        return ResponseEntity.ok(todoService.updateTodo(id, request, getUserIdFromHeader(req)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id,
                                           HttpServletRequest req) {
        todoService.deleteTodo(id, getUserIdFromHeader(req));
        return ResponseEntity.noContent().build();
    }

}
