package com.example.gptlearn.model.response;

import lombok.Getter;

@Getter
public class SingInResponse {
    private final String username;
    private final String accessToken;

    public SingInResponse(String username, String accessToken) {
        this.username = username;
        this.accessToken = "Bearer " + accessToken;
    }
}