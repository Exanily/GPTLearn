package com.example.gptlearn.entity;

import com.example.gptlearn.entity.key.TaskCompletionKey;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "task_completion")
public class TaskCompletion {

    @EmbeddedId
    private TaskCompletionKey key;

    @Column(name = "user_id", insertable = false, updatable = false)
    private long userId;

    @Column(name = "task_id", insertable = false, updatable = false)
    private long taskId;

    @Column(name = "completion_date", nullable = false)
    private Instant completionDate;
    //TODO: Instant

    @Column(name = "result", nullable = false)
    private String result;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "task_id", insertable = false, updatable = false)
    private Task task;
}