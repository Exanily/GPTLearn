package com.example.gptlearn.model.response;


import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class AllThemeResponse {
    List<String> themes;
}
