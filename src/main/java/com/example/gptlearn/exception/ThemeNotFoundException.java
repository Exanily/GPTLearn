package com.example.gptlearn.exception;

public class ThemeNotFoundException extends RuntimeException{
    public ThemeNotFoundException(String message) {
        super(message);
    }
}
