package com.example.gptlearn.entity.key;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class TaskCompletionKey implements Serializable {
    @Column(name = "user_id")
    private long userId;

    @Column(name = "task_id")
    private long taskId;

}
