package com.example.gptlearn.model.request;

import lombok.Data;
import lombok.NonNull;

@Data
public class SingUpRequest {
    @NonNull
    private String username;
    @NonNull
    private String password;
    @NonNull
    private String password2;

    public void setUsername(@NonNull String username) {
        this.username = username.trim();
    }

    public SingUpRequest(@NonNull String username, @NonNull String password, @NonNull String password2) {
        this.username = username.trim();
        this.password = password;
        this.password2 = password2;
    }
}
