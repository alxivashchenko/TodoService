package com.alexivashchenko.todo.service.service;

import com.alexivashchenko.todo.service.dto.TodoRequestDto;
import com.alexivashchenko.todo.service.dto.TodoResponseDto;

import java.util.List;

public interface TodoService {
    TodoResponseDto createTodo(TodoRequestDto request);

    TodoResponseDto getTodoById(Long id);

    List<TodoResponseDto> getAllTodos();

    TodoResponseDto updateTodo(Long id, TodoRequestDto request);

    void deleteTodo(Long id, Long userId);

    List<TodoResponseDto> getAllTodosByUserId(Long userId);
}
