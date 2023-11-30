package com.example.gptlearn.controller;

import com.example.gptlearn.mapper.TaskMapper;
import com.example.gptlearn.model.dto.TaskDto;
import com.example.gptlearn.model.dto.enums.Complexity;
import com.example.gptlearn.model.request.TaskAnswerRequest;
import com.example.gptlearn.model.response.TaskAnswerResponse;
import com.example.gptlearn.model.response.TasksResponse;
import com.example.gptlearn.service.TaskCompletionService;
import com.example.gptlearn.service.TaskGeneratorService;
import com.example.gptlearn.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskGeneratorService taskGeneratorService;
    private final TaskCompletionService taskCompletionService;
    private final TaskService taskService;

    @PostMapping
    @RequestMapping("/generate")
    public TasksResponse generate(@RequestParam String theme, @RequestParam Complexity complexity) {
        List<TaskDto> tasked = TaskMapper.INSTANCE.taskListToList(taskGeneratorService.generate(theme, complexity, 1));
        return TasksResponse.builder()
                .tasks(tasked)
                .build();
    }

    @PostMapping
    @RequestMapping("/answer")
    public TaskAnswerResponse answer(@RequestBody TaskAnswerRequest taskAnswerRequest) {
        Boolean answer = taskCompletionService.add(taskAnswerRequest.getTaskId(), taskAnswerRequest.getAnswer());
        return TaskAnswerResponse.builder()
                .answer(answer)
                .build();
    }

    @GetMapping
    public TasksResponse getAll() {
        List<TaskDto> tasked = TaskMapper.INSTANCE.taskListToList(taskService.getTaskAll());
        return TasksResponse.builder()
                .tasks(tasked)
                .build();
    }
}
