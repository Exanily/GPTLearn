package com.example.gptlearn.model.response;

import com.example.gptlearn.model.dto.Message;
import lombok.Data;

import java.util.List;

@Data
public class ChatResponse {
    private List<Choice> choices;

    @Data
    public static class Choice {
        private int index;
        private Message message;

    }
}
