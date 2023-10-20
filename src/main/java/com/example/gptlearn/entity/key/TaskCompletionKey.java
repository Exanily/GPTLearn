package com.example.gptlearn.entity.key;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class TaskCompletionKey implements Serializable {
    @Column(name = "user_id")
    private long userId;

    @Column(name = "task_id")
    private long taskId;

}
