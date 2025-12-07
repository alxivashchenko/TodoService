package com.alexivashchenko.todo.service.service;

import com.alexivashchenko.todo.service.dto.TodoRequestDto;
import com.alexivashchenko.todo.service.dto.TodoResponseDto;

import java.util.List;

public interface TodoService {
    List<TodoResponseDto> getAllTodosByUserId(String userId) ;

//    List<TodoResponseDto> getAllTodos(String userId);

    TodoResponseDto getTodoById(Long id, String userId);

    TodoResponseDto createTodo(TodoRequestDto request, String userId);

    TodoResponseDto updateTodo(Long id, TodoRequestDto request, String userId);

    void deleteTodo(Long id, String userId);

}
