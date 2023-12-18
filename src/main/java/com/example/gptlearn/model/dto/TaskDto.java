package com.example.gptlearn.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
    private Long id;
    private String theme;
    private String complexity;
    private String description;
    private String hint;
    private String solution;
    private String title;
}
