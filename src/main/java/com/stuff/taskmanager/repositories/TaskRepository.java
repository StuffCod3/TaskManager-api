package com.stuff.taskmanager.repositories;

import com.stuff.taskmanager.models.Task;
import com.stuff.taskmanager.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findByTitle(String title);
    Optional<Task> findByAuthor(User user);
    Optional<Task> findByAssignee(User user);

    List<Task> findAllByAuthor(User user);

    Optional<Task> findById(Long id);
}
