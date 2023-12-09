package com.stuff.taskmanager.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@Table(name = "comments")
@RequiredArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "comment")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "usr")
    private User user;

    @ManyToOne
    @JoinColumn(name = "tsk")
    private Task task;
}
