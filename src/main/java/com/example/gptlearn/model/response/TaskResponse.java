package com.example.gptlearn.model.response;

import com.example.gptlearn.model.dto.TaskDto;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TaskResponse {
    private TaskDto task;
}
