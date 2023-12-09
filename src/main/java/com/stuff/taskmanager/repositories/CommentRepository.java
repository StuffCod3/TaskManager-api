package com.stuff.taskmanager.repositories;

import com.stuff.taskmanager.models.Comment;
import com.stuff.taskmanager.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findById(Long id);
    List<Comment> findByTaskId(Long taskId);
}
