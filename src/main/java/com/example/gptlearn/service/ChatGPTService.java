package com.example.gptlearn.service;

import com.example.gptlearn.exception.ChatGPTResponseException;
import com.example.gptlearn.model.request.ChatRequest;
import com.example.gptlearn.model.response.ChatResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
@Log4j2
public class ChatGPTService {
    private final RestTemplate restTemplate;

    @Autowired
    public ChatGPTService(@Qualifier("openaiRestTemplate") RestTemplate restTemplate,
                          @Value("${app.openai.model}") String model,
                          @Value("${app.openai.api.url}") String apiUrl) {
        this.restTemplate = restTemplate;
        this.model = model;
        this.apiUrl = apiUrl;
    }

    private final String model;
    private final String apiUrl;


    public ChatResponse getResponse(String message, int n) {
        ChatRequest request = new ChatRequest(model, message, n);
        HttpEntity<ChatRequest> httpEntity = new HttpEntity<>(request);
        ResponseEntity<ChatResponse> response = restTemplate.exchange(apiUrl, HttpMethod.POST, httpEntity, ChatResponse.class);
        if (!response.getStatusCode().is2xxSuccessful()){
            log.error("message request: {} \n response: {}" , message, response);
            throw new ChatGPTResponseException(Objects.requireNonNull(response.getBody()).toString());
        }
        return response.getBody();
    }

}
