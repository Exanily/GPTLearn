package com.example.gptlearn.controller;


import com.example.gptlearn.model.request.SingInRequest;
import com.example.gptlearn.model.request.SingUpRequest;
import com.example.gptlearn.model.response.SingInResponse;
import com.example.gptlearn.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("singUp")
    public void singUp(@RequestBody SingUpRequest singUpRequest) {
        authService.singUp(singUpRequest);
    }

    @PostMapping("singIn")
    public SingInResponse singIn(@RequestBody SingInRequest singInRequest) {
        return authService.singIn(singInRequest);
    }
}
