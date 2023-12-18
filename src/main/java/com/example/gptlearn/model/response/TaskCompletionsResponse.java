package com.example.gptlearn.model.response;

import com.example.gptlearn.model.dto.PageDto;
import com.example.gptlearn.model.dto.TaskCompletionDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class TaskCompletionsResponse {
    private List<TaskCompletionDto> tasks;
    private PageDto page;
}
