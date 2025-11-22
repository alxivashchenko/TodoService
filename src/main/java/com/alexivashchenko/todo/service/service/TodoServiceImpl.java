package com.alexivashchenko.todo.service.service;

import com.alexivashchenko.todo.service.dto.TodoRequestDto;
import com.alexivashchenko.todo.service.dto.TodoResponseDto;
import com.alexivashchenko.todo.service.exception.TodoNotFoundException;
import com.alexivashchenko.todo.service.mapper.TodoMapper;
import com.alexivashchenko.todo.service.model.Status;
import com.alexivashchenko.todo.service.model.Todo;
import com.alexivashchenko.todo.service.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;
    private final TodoMapper todoMapper;

    @Override
    public TodoResponseDto createTodo(TodoRequestDto request) {
        Todo todo = todoMapper.toEntity(request);
        if (todoRepository.existsByTitleAndUserId(request.title(), request.userId())) {
            throw new IllegalArgumentException("Todo with title " + request.title() +
                    " already exists for user with id " + request.userId());
        }
        return todoMapper.toResponseDto(todoRepository.save(todo));
    }

    @Override
    public TodoResponseDto getTodoById(Long id) {
        return todoRepository.findById(id)
                .map(todoMapper::toResponseDto)
                .orElseThrow(() -> new TodoNotFoundException(id));
    }

    @Override
    public List<TodoResponseDto> getAllTodos() {
        return todoRepository.findAll()
                .stream()
                .map(todoMapper::toResponseDto)
                .toList();
    }

    @Override
    public TodoResponseDto updateTodo(Long id, TodoRequestDto request) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException(id));
        if (!todo.getUserId().equals(request.userId())) {
            throw new SecurityException("You are not allowed to modify this todo");
        }
        validateStatusTransition(todo.getStatus(), request.status());
        todo.setTitle(request.title());
        todo.setDescription(request.description());
        todo.setStatus(request.status());
        todo.setUserId(request.userId());

        return todoMapper.toResponseDto(todoRepository.save(todo));
    }

    @Override
    public void deleteTodo(Long id, Long userId) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException(id));
        if (!todo.getUserId().equals(userId)) {
            throw new SecurityException("You are not allowed to delete this todo");
        }
        todoRepository.deleteById(id);
    }

    @Override
    public List<TodoResponseDto> getAllTodosByUserId(Long userId) {
        return todoRepository.findAll()
                .stream()
                .filter(todo -> todo.getUserId().equals(userId))
                .map(todoMapper::toResponseDto)
                .toList();
    }

    private void validateStatusTransition(Status oldStatus, Status newStatus) {
//        if (oldStatus == Status.CANCELED && newStatus != Status.CANCELED) {
//            throw new IllegalStateException("Cannot reopen a canceled todo");
//        }
//        if (oldStatus == Status.COMPLETED && newStatus != Status.COMPLETED) {
//            throw new IllegalStateException("Cannot reopen a completed todo");
//        }
    }
}
