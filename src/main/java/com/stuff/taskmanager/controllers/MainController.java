package com.stuff.taskmanager.controllers;

import com.stuff.taskmanager.dtos.AuthDto;
import com.stuff.taskmanager.dtos.CommentDto;
import com.stuff.taskmanager.dtos.TaskDto;
import com.stuff.taskmanager.models.Comment;
import com.stuff.taskmanager.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/task")
@RequiredArgsConstructor
public class MainController {
    private final TaskService taskService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody TaskDto taskDto, @RequestParam String token){
        return ResponseEntity.ok(taskService.createTask(taskDto, token));
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody TaskDto taskDto, @RequestParam String token){
        return ResponseEntity.ok(taskService.updateTask(taskDto, token));
    }

    @PostMapping("/find")
    public ResponseEntity<?> find(@RequestBody TaskDto taskDto, @RequestParam String token){
        return ResponseEntity.ok(taskService.findTask(taskDto, token));
    }

    @PostMapping("/showAll")
    public ResponseEntity<?> showAll(@RequestParam String token){
        return ResponseEntity.ok(taskService.showAllTasks(token));
    }

    @PostMapping("/tasksAuthor")
    public ResponseEntity<?> tasksAuthor(@RequestParam String token){
        return ResponseEntity.ok(taskService.getTasksByAuthor(token));
    }

    @PostMapping("/deleteTask")
    public ResponseEntity<?> deleteTask(@RequestBody TaskDto taskDto, @RequestParam String token){
        return ResponseEntity.ok(taskService.deleteTask(taskDto, token));
    }

    @PostMapping("/createComment")
    public ResponseEntity<?> createComment(@RequestBody CommentDto commentDto, @RequestParam String token){
        return ResponseEntity.ok(taskService.addComment(commentDto, token));
    }

    @GetMapping("/commentsTask")
    public ResponseEntity<List<Comment>> getAllCommentsForTask(@RequestParam Long taskId) {
        List<Comment> comments = taskService.getAllCommentsForTask(taskId);
        return ResponseEntity.ok(comments);
    }
}
