package com.example.gptlearn.controller;

import com.example.gptlearn.service.UserRolesService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/moder")
@RequiredArgsConstructor
public class AdminController {

    private final UserRolesService userRolesService;

    @PostMapping
    @Secured({"ROLE_ADMIN"})
    public void add(@RequestParam("username") String username) {
        userRolesService.addModer(username);
    }

    @DeleteMapping
    @Secured({"ROLE_ADMIN"})
    public void delete(@RequestParam("username") String username) {
        userRolesService.deleteModer(username);
    }
}
