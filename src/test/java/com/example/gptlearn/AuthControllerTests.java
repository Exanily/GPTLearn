package com.example.gptlearn;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.yaml")
public class AuthControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void singUpResponse400PasswordMismatch() throws Exception {
        JSONObject object = new JSONObject();
        object.put("username","asd");
        object.put("password","1");
        object.put("password2","2");
        this.mockMvc.perform(post("/auth/singUp").contentType(MediaType.APPLICATION_JSON)
                        .content(object.toString()))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("error", "Пароли не совпадают").exists())
        ;
    }

    @Test
    void singUpResponse200() throws Exception {
        JSONObject object = new JSONObject();
        object.put("username","asd");
        object.put("password","1");
        object.put("password2","1");
        this.mockMvc.perform(post("/auth/singUp").contentType(MediaType.APPLICATION_JSON)
                        .content(object.toString()))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @Test
    void singUpResponse400UsernameBusy() throws Exception {
        JSONObject object = new JSONObject();
        object.put("username","asd");
        object.put("password","1");
        object.put("password2","1");
        this.mockMvc.perform(post("/auth/singUp").contentType(MediaType.APPLICATION_JSON)
                        .content(object.toString()))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("error", "Имя пользователя занято").exists())
        ;
    }
}
