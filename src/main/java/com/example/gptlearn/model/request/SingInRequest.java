package com.example.gptlearn.model.request;

import lombok.Data;
import lombok.NonNull;

@Data
public class SingInRequest {
    @NonNull
    private String username;
    @NonNull
    private String password;

}
