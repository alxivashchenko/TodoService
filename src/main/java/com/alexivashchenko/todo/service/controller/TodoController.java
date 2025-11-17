package com.alexivashchenko.todo.service.controller;

import com.alexivashchenko.todo.service.dto.TodoRequestDto;
import com.alexivashchenko.todo.service.dto.TodoResponseDto;
import com.alexivashchenko.todo.service.service.TodoService;
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

    @PostMapping
    public ResponseEntity<TodoResponseDto> createTodo(@Valid @RequestBody TodoRequestDto request) {
        return ResponseEntity.ok(todoService.createTodo(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoResponseDto> getTodoById(@PathVariable Long id) {
        return ResponseEntity.ok(todoService.getTodoById(id));
    }

    @GetMapping
    public ResponseEntity<List<TodoResponseDto>> getAllTodos() {
        return ResponseEntity.ok(todoService.getAllTodos());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TodoResponseDto>> getAllTodosByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(todoService.getAllTodosByUserId(userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoResponseDto> updateTodo(@PathVariable Long id,
                                                      @Valid @RequestBody TodoRequestDto request) {
        return ResponseEntity.ok(todoService.updateTodo(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id,
                                           @RequestParam Long userId) {
        todoService.deleteTodo(id, userId);
        return ResponseEntity.noContent().build();
    }

}
