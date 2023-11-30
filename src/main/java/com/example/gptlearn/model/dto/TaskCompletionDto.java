package com.example.gptlearn.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskCompletionDto {
    private Date completionDate;
    private Boolean result;
    private String answer;
    private TaskDto task;
}
