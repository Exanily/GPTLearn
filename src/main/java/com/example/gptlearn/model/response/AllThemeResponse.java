package com.example.gptlearn.model.response;


import lombok.Builder;

import java.util.List;

@Builder
public class AllThemeResponse {
    List<String> themes;
}
