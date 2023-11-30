package com.example.gptlearn.controller;

import com.example.gptlearn.service.UserRolesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/moder")
@RequiredArgsConstructor
public class AdminController {

    private final UserRolesService userRolesService;

    @PostMapping
    public void add(@RequestParam String username) {
        userRolesService.addModer(username);
    }

    @DeleteMapping
    public void delete(@RequestParam String username) {
        userRolesService.deleteModer(username);
    }
}
