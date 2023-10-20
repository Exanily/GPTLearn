package com.example.gptlearn.model.dto;

import lombok.Getter;

@Getter
public enum Complexity {
    EASY("легко"),
    NORMAL("нормально"),
    HARD("сложно");
    private final String value;

    Complexity(String value) {
        this.value = value;
    }
}
