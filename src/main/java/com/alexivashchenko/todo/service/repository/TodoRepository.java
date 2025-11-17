package com.alexivashchenko.todo.service.repository;

import com.alexivashchenko.todo.service.model.Todo;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    boolean existsByTitleAndUserId(@NotBlank String title, Long aLong);
}
