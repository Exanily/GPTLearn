package com.example.gptlearn.exception;

public class UsernameBusyException extends RuntimeException{
    public UsernameBusyException(String message) {
        super(message);
    }
}
