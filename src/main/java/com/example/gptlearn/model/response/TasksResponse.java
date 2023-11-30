package com.example.gptlearn.model.response;

import com.example.gptlearn.model.dto.TaskDto;
import lombok.*;

import java.util.List;

@Builder
@Getter
public class TasksResponse {
    private List<TaskDto> tasks;
}
