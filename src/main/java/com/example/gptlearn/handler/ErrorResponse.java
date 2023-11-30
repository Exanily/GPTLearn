package com.example.gptlearn.handler;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Builder
@Getter
public class ErrorResponse {
    private final String error;
    private final Date date;
}
