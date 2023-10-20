package com.example.gptlearn.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/profile")
public class ProfileController {

    @GetMapping("/me")
    public ResponseEntity<?> me() {
        return null;
    }
}
