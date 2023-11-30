package com.example.gptlearn.model.request;

import lombok.Data;

@Data
public class TaskAnswerRequest {
    private Long taskId;
    private String answer;
}
