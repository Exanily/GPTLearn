package com.example.gptlearn.model.request;

import lombok.Data;
import lombok.NonNull;

@Data
public class SingInRequest {
    @NonNull
    private String username;
    @NonNull
    private String password;

    public SingInRequest(@NonNull String username, @NonNull String password) {
        this.username = username.trim();
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username.trim();
    }
}
