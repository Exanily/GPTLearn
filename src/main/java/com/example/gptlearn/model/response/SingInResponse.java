package com.example.gptlearn.model.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SingInResponse {
    private String username;
    private String token;

}