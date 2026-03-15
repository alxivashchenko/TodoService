package com.alexivashchenko.todo.service.service;

import com.alexivashchenko.todo.service.dto.TodoRequestDto;
import com.alexivashchenko.todo.service.dto.TodoResponseDto;
import com.alexivashchenko.todo.service.exception.ForbiddenException;
import com.alexivashchenko.todo.service.exception.TodoNotFoundException;
import com.alexivashchenko.todo.service.mapper.TodoMapper;
import com.alexivashchenko.todo.service.model.Status;
import com.alexivashchenko.todo.service.model.Todo;
import com.alexivashchenko.todo.service.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;
    private final TodoMapper todoMapper;


    @Override
    public List<TodoResponseDto> getAllTodosByUserId(UUID userId) {
        return todoRepository.findAllByUserId(userId)
                .stream()
                .map(todoMapper::toResponseDto)
                .toList();
    }

    @Override
    public TodoResponseDto getTodoById(Long id, UUID userId) {
        return todoRepository.findByIdAndUserId(id, userId)
                .map(todoMapper::toResponseDto)
                .orElseThrow(() -> new TodoNotFoundException(id));
    }

    @Override
    public TodoResponseDto createTodo(TodoRequestDto request, UUID userId) {
        Todo todo = todoMapper.toEntity(request);
        todo.setUserId(userId); // assign owner from JWT
        if (todoRepository.existsByTitleAndUserId(request.title(), userId)) {
            throw new IllegalArgumentException("Todo with title " + request.title() +
                    " already exists for current user");
        }
        return todoMapper.toResponseDto(todoRepository.save(todo));
    }

    @Override
    public TodoResponseDto updateTodo(Long id, TodoRequestDto request, UUID userId) {
        Todo todo = todoRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ForbiddenException("You cannot modify another user's todo"));
        validateStatusTransition(todo.getStatus(), request.status());
        todo.setTitle(request.title());
        todo.setDescription(request.description());
        todo.setStatus(request.status());

        return todoMapper.toResponseDto(todoRepository.save(todo));
    }

    @Override
    public void deleteTodo(Long id, UUID userId) {
        Todo todo = todoRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ForbiddenException("You cannot delete another user's todo"));
        todoRepository.delete(todo);
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
