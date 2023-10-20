package com.example.gptlearn.controller;


import com.example.gptlearn.model.request.SingInRequest;
import com.example.gptlearn.model.request.SingUpRequest;
import com.example.gptlearn.model.response.ErrorResponse;
import com.example.gptlearn.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> singUp(@RequestBody SingUpRequest singUpRequest) {
        try {
            authService.singUp(singUpRequest);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    @PostMapping("singIn")
    public ResponseEntity<?> singIn(@RequestBody SingInRequest singInRequest) {
        try {
            return ResponseEntity.ok(authService.singIn(singInRequest));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }
}
