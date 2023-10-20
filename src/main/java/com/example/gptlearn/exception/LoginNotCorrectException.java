package com.example.gptlearn.exception;

public class LoginNotCorrectException extends RuntimeException{
    public LoginNotCorrectException(String message) {
        super(message);
    }
}
