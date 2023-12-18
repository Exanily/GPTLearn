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
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@Log4j2
public class ChatGPTCommunicationService {
    private final RestTemplate restTemplate;

    @Autowired
    public ChatGPTCommunicationService(@Qualifier("openaiRestTemplate") RestTemplate restTemplate,
                                       @Value("${app.openai.model}") String model,
                                       @Value("${app.openai.api.url}") String apiUrl) {
        this.restTemplate = restTemplate;
        this.model = model;
        this.apiUrl = apiUrl;
    }

    private final String model;
    private final String apiUrl;


    public ChatResponse getResponse(String message, int n) {
        ChatRequest request = new ChatRequest(model, message, n, 1);
        HttpEntity<ChatRequest> httpEntity = new HttpEntity<>(request);
        ResponseEntity<ChatResponse> response;
        try {
            response = restTemplate.exchange(apiUrl, HttpMethod.POST, httpEntity, ChatResponse.class);
        } catch (RestClientException exception) {
            log.error("Message request: {} \n Response: {}", message, exception.getMessage());
            throw new ChatGPTResponseException("Ошибка на сервере");
        }
        return response.getBody();
    }

}
