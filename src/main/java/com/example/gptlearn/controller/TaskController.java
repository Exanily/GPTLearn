package com.example.gptlearn.controller;

import com.example.gptlearn.mapper.TaskMapper;
import com.example.gptlearn.model.dto.TaskDto;
import com.example.gptlearn.model.dto.enums.Complexity;
import com.example.gptlearn.model.request.TaskAnswerRequest;
import com.example.gptlearn.model.response.TaskAnswerResponse;
import com.example.gptlearn.model.response.TaskGenerateResponse;
import com.example.gptlearn.model.response.TaskResponse;
import com.example.gptlearn.model.response.TasksResponse;
import com.example.gptlearn.service.TaskCompletionService;
import com.example.gptlearn.service.TaskManagementService;
import com.example.gptlearn.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskManagementService taskManagementService;
    private final TaskCompletionService taskCompletionService;
    private final TaskService taskService;

    @PostMapping("/generate")
    public TaskGenerateResponse generate(@RequestParam String theme, @RequestParam Complexity complexity) {
        List<TaskDto> tasked = TaskMapper.INSTANCE.taskListToList(taskManagementService.generate(theme, complexity, 1));
        return TaskGenerateResponse.builder()
                .tasks(tasked)
                .build();
    }

    @PostMapping("/answer")
    public TaskAnswerResponse answer(@RequestBody TaskAnswerRequest taskAnswerRequest) {
        Boolean answer = taskCompletionService.add(taskAnswerRequest.getTaskId(), taskAnswerRequest.getAnswer());
        return TaskAnswerResponse.builder()
                .answer(answer)
                .build();
    }

    @GetMapping("/all")
    public TasksResponse getAll(@RequestParam("page_number") int pageNumber, @RequestParam(name = "page_size" , defaultValue = "5") int pageSize) {
        return taskService.getTaskAll(pageNumber, pageSize);
    }

    @GetMapping
    public TaskResponse getById(@RequestParam long id) {
        return TaskResponse.builder()
                .task(TaskMapper.INSTANCE.taskToDto(taskService.getTaskById(id)))
                .build();
    }

    @DeleteMapping
    @Secured({"ROLE_ADMIN", "ROLE_MODER"})
    public void delete(Long id) {
        taskService.delete(id);
    }

    @DeleteMapping("/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_MODER"})
    public void deletePath(@PathVariable("id") Long id) {
        taskService.delete(id);
    }
}
