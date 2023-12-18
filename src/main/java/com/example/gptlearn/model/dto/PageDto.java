package com.example.gptlearn.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageDto {
    private int totalPages;
    private long totalElements;
    private int number;
    private boolean isEmpty;
    private boolean isFirst;
    private boolean isLast;
}
