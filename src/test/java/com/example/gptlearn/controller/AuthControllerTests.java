package com.example.gptlearn.controller;

import com.example.gptlearn.AbstractTest;
import com.example.gptlearn.entity.User;
import com.example.gptlearn.repository.UserRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthControllerTests extends AbstractTest {

    private static final String URL_AUTH = "/auth";
    private static final String URL_SING_UP = URL_AUTH + "/singUp";
    private static final String URL_SING_IN = URL_AUTH + "/singIn";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String BAD_PASSWORD = PASSWORD + 1;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    protected UserRepository userRepository;

    @AfterEach
    void clearUserRepo() {
        userRepository.deleteAll();
    }


    @Test
    void should_returnBadRequest_when_singUpPasswordMismatch() throws Exception {
        JSONObject object = singUpJSON(BAD_PASSWORD);
        this.mockMvc.perform(post(URL_SING_UP).contentType(MediaType.APPLICATION_JSON)
                        .content(object.toString()))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("error", "Пароли не совпадают").exists())
        ;
    }

    @Test
    void should_returnOk_when_singUpPasswordsMatch() throws Exception {
        JSONObject object = singUpJSON(PASSWORD);
        this.mockMvc.perform(post(URL_SING_UP).contentType(MediaType.APPLICATION_JSON)
                        .content(object.toString()))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @Test
    void should_returnBadRequest_when_singUpUsernameBusy() throws Exception {
        userRepository.save(newUser());

        JSONObject user = singUpJSON(PASSWORD);
        this.mockMvc.perform(post(URL_SING_UP).contentType(MediaType.APPLICATION_JSON)
                        .content(user.toString()))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("error", "Имя пользователя занято").exists())
        ;
    }

    @Test
    void should_returnOk_when_singInCorrectData() throws Exception {
        userRepository.save(newUser());

        JSONObject object = singInJSON(PASSWORD);
        this.mockMvc.perform(post(URL_SING_IN).contentType(MediaType.APPLICATION_JSON)
                        .content(object.toString()))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @Test
    void should_returnBadRequest_when_singInNotCorrectData() throws Exception {
        userRepository.save(newUser());

        JSONObject object = singInJSON(BAD_PASSWORD);
        this.mockMvc.perform(post(URL_SING_IN).contentType(MediaType.APPLICATION_JSON)
                        .content(object.toString()))
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;
    }


    private JSONObject singInJSON(String password) throws JSONException {
        JSONObject object = new JSONObject();
        object.put("username", AuthControllerTests.USERNAME);
        object.put("password", password);
        return object;
    }

    private JSONObject singUpJSON(String password2) throws JSONException {
        JSONObject object = singInJSON(AuthControllerTests.PASSWORD);
        object.put("password2", password2);
        return object;
    }

    private User newUser() {
        return new User(1L, USERNAME, passwordEncoder.encode(PASSWORD), new Date(), null, null);
    }
}
