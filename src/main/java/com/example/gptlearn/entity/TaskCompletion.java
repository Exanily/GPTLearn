package com.example.gptlearn.entity;

import com.example.gptlearn.entity.key.TaskCompletionKey;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "task_completion")
@AllArgsConstructor
@NoArgsConstructor
public class TaskCompletion {

    @EmbeddedId
    private TaskCompletionKey key;

    @Column(name = "completion_date", nullable = false)
    private Date completionDate;

    @Column(name = "result", nullable = false)
    private Boolean result;

    @Column(name = "answer", nullable = false)
    private String answer;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "task_id", insertable = false, updatable = false)
    private Task task;
}