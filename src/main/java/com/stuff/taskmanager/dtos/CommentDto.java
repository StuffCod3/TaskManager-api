package com.stuff.taskmanager.dtos;

import com.stuff.taskmanager.models.Task;
import lombok.Data;

@Data
public class CommentDto {
    private Long tsk;
    private String comment;
}
