package com.stuff.taskmanager.services;

import com.stuff.taskmanager.dtos.CommentDto;
import com.stuff.taskmanager.dtos.TaskDto;
import com.stuff.taskmanager.models.Comment;
import com.stuff.taskmanager.models.Task;
import com.stuff.taskmanager.models.User;
import com.stuff.taskmanager.repositories.CommentRepository;
import com.stuff.taskmanager.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserService userService;
    private final CommentRepository commentRepository;

    public ResponseEntity<?> createTask(TaskDto taskDto, String token){
        Task task = new Task();
        Optional<User> author = userService.findByToken(token);
        if(!taskDto.getTitle().isEmpty()){
            task.setTitle(taskDto.getTitle());
            task.setDescription(taskDto.getDescription());
            task.setStatus(taskDto.getStatus());
            task.setAuthor(author.get());
            return ResponseEntity.ok(taskRepository.save(task));
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    public ResponseEntity<?> updateTask(TaskDto taskDto, String token){
        Optional<Task> task = taskRepository.findById(taskDto.getId());
        Optional<User> author = userService.findByToken(token);
        Optional<User> assignee = userService.findByEmail(taskDto.getAssignee());

        if (task.isPresent() && author.isPresent()){
            if (taskDto.getTitle() != null && !taskDto.getTitle().equals(task.get().getTitle())) {
                task.get().setTitle(taskDto.getTitle());
            }
            if (taskDto.getDescription() != null && !taskDto.getDescription().equals(task.get().getDescription())) {
                task.get().setDescription(taskDto.getDescription());
            }
            if (taskDto.getStatus() != null && !taskDto.getStatus().equals(task.get().getStatus())) {
                task.get().setStatus(taskDto.getStatus());
            }
            if (author.isPresent() && !author.get().equals(task.get().getAuthor())) {
                task.get().setAuthor(author.get());
            }
            if (assignee.isPresent() && !assignee.get().equals(task.get().getAssignee())) {
                task.get().setAssignee(assignee.get());
            }
            if (taskRepository.existsById(task.get().getId())) {
                return ResponseEntity.ok(taskRepository.save(task.get()));
            } else {
                return ResponseEntity.notFound().build();
            }
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<?> findTask(TaskDto taskDto, String token){
        if(taskDto.getId() != null && userService.findByToken(token).isPresent()){
            Optional<Task> task = taskRepository.findById(taskDto.getId());
            return ResponseEntity.ok(task);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    public ResponseEntity<?> showAllTasks(String token){
        Optional<List<Task>> task = Optional.of(taskRepository.findAll());
        Optional<User> author = userService.findByToken(token);
        if(author.isPresent()){
            return ResponseEntity.ok(task);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    public ResponseEntity<List<Task>> getTasksByAuthor(String token) {
        Optional<User> author = userService.findByToken(token);
        if (author.isPresent()) {
            List<Task> tasks = taskRepository.findAllByAuthor(author.get());
            return ResponseEntity.ok(tasks);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    public ResponseEntity<?> deleteTask(TaskDto taskDto, String token){
        Optional<Task> task = taskRepository.findById(taskDto.getId());
        Optional<User> author = userService.findByToken(token);
        if (task.get().getAuthor().equals(author.get())){
            taskRepository.delete(task.get());
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.noContent().build();
        }
    }

    public ResponseEntity<?> addComment(CommentDto commentDto, String token){
        if (commentDto == null || token == null) {
            return ResponseEntity.badRequest().body("commentDto or token is null");
        }
        Comment comment = new Comment();
        Optional<User> usr = userService.findByToken(token);
        Optional<Task> tsk = taskRepository.findById(commentDto.getTsk());
        if(!commentDto.getComment().isEmpty()){
            comment.setComment(commentDto.getComment());
            comment.setTask(tsk.get());
            comment.setUser(usr.get());
            return ResponseEntity.ok(commentRepository.save(comment));
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    public List<Comment> getAllCommentsForTask(Long taskId) {
        return commentRepository.findByTaskId(taskId);
    }
}
