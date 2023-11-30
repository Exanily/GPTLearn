package com.example.gptlearn.model.dto.enums;

import lombok.Getter;

@Getter
public enum Complexity {
    SIMPLE("легко"),
    NORMAL("нормально"),
    HARD("сложно");
    private final String value;

    Complexity(String value) {
        this.value = value;
    }
}
