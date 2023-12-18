package com.example.gptlearn.model.request;

import com.example.gptlearn.model.dto.Message;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChatRequest {
    private String model;
    private List<Message> messages;
    private int n;
    private double temperature;

    public ChatRequest(String model, String prompt, int n, double temperature) {
        this.model = model;
        this.n = n;
        this.temperature = temperature;
        this.messages = new ArrayList<>();
        this.messages.add(new Message("user", prompt));
    }
}
