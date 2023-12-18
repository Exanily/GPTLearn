package com.example.gptlearn.controller;

import com.example.gptlearn.AbstractTest;
import com.example.gptlearn.entity.Theme;
import com.example.gptlearn.repository.ThemeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;


import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    void should_returnForbidden_when_addThemeToUser() throws Exception {
        this.mockMvc.perform(post("/theme").param("name", "география"))
                .andDo(print())
                .andExpect(status().isForbidden())
        ;
    }

    @Test
    @WithMockUser(username = "moder", roles = "MODER")
    void should_returnOk_when_addModer() throws Exception {
        String name = "география";
        this.mockMvc.perform(post("/theme").param("name", name))
                .andDo(print())
                .andExpect(status().isOk())
        ;

        assertThat(themeRepository.findByName(name)).isNotEmpty();
    }


    @Test
    @WithMockUser(username = "moder", roles = "MODER")
    void should_returnExceptionExist_when_addThemeExist() throws Exception {
        String name = "география";
        createAndSaveTheme(name);

        this.mockMvc.perform(post("/theme").param("name", name))
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

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void should_returnOk_when_deleteTheme() throws Exception {
        String name = "география";
        createAndSaveTheme(name);

        this.mockMvc.perform(delete("/theme").param("name", name))
                .andDo(print())
                .andExpect(status().isOk())
        ;

        assertThat(themeRepository.findByName(name)).isEmpty();
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void should_returnOk_when_deleteThemeToUrl() throws Exception {
        String name = "география";
        Theme theme = createAndSaveTheme(name);

        this.mockMvc.perform(delete("/theme/" + theme.getId()))
                .andDo(print())
                .andExpect(status().isOk())
        ;

        assertThat(themeRepository.findByName(name)).isEmpty();
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void should_returnOk_when_deleteNotExistTheme() throws Exception {
        String name = "география";
        themeRepository.deleteByName(name);

        this.mockMvc.perform(delete("/theme").param("name", name))
                .andDo(print())
                .andExpect(status().isOk())
        ;

        assertThat(themeRepository.findByName(name)).isEmpty();
    }


    private Theme createAndSaveTheme(String name) {
        return saveTheme(createTheme(name));
    }

    private Theme createTheme(String name) {
        Theme theme = new Theme();
        theme.setName(name);
        return theme;
    }

    private Theme saveTheme(Theme theme) {
        return themeRepository.save(theme);
    }
}
