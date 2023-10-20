package com.example.gptlearn.model.response;

import lombok.Builder;

import java.io.Serializable;

@Builder
public record ErrorResponse(String error) implements Serializable {
}
