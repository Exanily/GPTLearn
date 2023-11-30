package com.example.gptlearn.controller;

import com.example.gptlearn.AbstractTest;
import com.example.gptlearn.entity.Theme;
import com.example.gptlearn.repository.ThemeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ThemeControllerTest extends AbstractTest {

    @Autowired
    protected ThemeRepository themeRepository;

    @AfterEach
    public void clearDB() {
        themeRepository.deleteAll();
    }

    @Test
    @WithAnonymousUser
    void should_returnForbidden_when_getAllAnonymousUser() throws Exception {
        this.mockMvc.perform(get("/theme"))
                .andDo(print())
                .andExpect(status().isUnauthorized())
        ;
    }

    @Test
    @WithMockUser(username = "user")
    void should_returnOk_when_getAllUser() throws Exception {
        this.mockMvc.perform(get("/theme"))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void should_returnForbidden_when_addUser() throws Exception {
        this.mockMvc.perform(post("/theme").param("name", "география"))
                .andDo(print())
                .andExpect(status().isForbidden())
        ;
    }

    @Test
    @WithMockUser(username = "moder", roles = "MODER")
    void should_returnOk_when_addModer() throws Exception {
        this.mockMvc.perform(post("/theme").param("name", "география"))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }


    @Test
    @WithMockUser(username = "moder", roles = "MODER")
    void should_returnExceptionExist_when_addThemeExist() throws Exception {
        Theme theme = new Theme();
        theme.setName("география");
        themeRepository.save(theme);

        this.mockMvc.perform(post("/theme").param("name", "география"))
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void should_returnOk_when_addAdmin() throws Exception {
        this.mockMvc.perform(post("/theme").param("name", "география2"))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }
}
