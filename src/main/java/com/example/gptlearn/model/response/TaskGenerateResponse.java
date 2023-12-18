package com.example.gptlearn.model.response;

import com.example.gptlearn.model.dto.TaskDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class TaskGenerateResponse {
    private List<TaskDto> tasks;
}
