package com.alexivashchenko.todo.service.service;

import com.alexivashchenko.todo.service.dto.TodoRequestDto;
import com.alexivashchenko.todo.service.dto.TodoResponseDto;

import java.util.List;
import java.util.UUID;

public interface TodoService {
    List<TodoResponseDto> getAllTodosByUserId(UUID userId);

//    List<TodoResponseDto> getAllTodos(UUID userId);

    TodoResponseDto getTodoById(Long id, UUID userId);

    TodoResponseDto createTodo(TodoRequestDto request, UUID userId);

    TodoResponseDto updateTodo(Long id, TodoRequestDto request, UUID userId);

    void deleteTodo(Long id, UUID userId);

}
