package com.example.gptlearn.controller;

import com.example.gptlearn.model.dto.UserDto;
import com.example.gptlearn.model.response.TaskCompletionsResponse;
import com.example.gptlearn.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/me")
    public UserDto me() {
        return profileService.getMe();
    }

    @GetMapping("/tasks")
    public TaskCompletionsResponse tasks(@RequestParam("page_number") int pageNumber, @RequestParam(name = "page_size", defaultValue = "5") int pageSize) {
        return profileService.getTaskCompletions(pageNumber, pageSize);
    }
}
