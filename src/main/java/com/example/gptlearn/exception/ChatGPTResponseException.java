package com.example.gptlearn.exception;

public class ChatGPTResponseException extends RuntimeException{
    public ChatGPTResponseException(String message) {
        super(message);
    }
}
