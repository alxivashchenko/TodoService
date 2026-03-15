package com.alexivashchenko.todo.service.repository;

import com.alexivashchenko.todo.service.model.Todo;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    boolean existsByTitleAndUserId(@NotBlank String title, UUID userId);

    List<Todo> findAllByUserId(UUID userId);

    Optional<Todo> findByIdAndUserId(Long id, UUID userId);
}
