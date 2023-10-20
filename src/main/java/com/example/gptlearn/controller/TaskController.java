package com.example.gptlearn.controller;

import com.example.gptlearn.model.dto.Complexity;
import com.example.gptlearn.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<?> generate(@RequestParam String theme, @RequestParam Complexity complexity) {
        try {
            return ResponseEntity.ok(taskService.generate(theme, complexity));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
