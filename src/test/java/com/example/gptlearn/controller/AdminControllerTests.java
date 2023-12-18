package com.example.gptlearn.controller;

import com.example.gptlearn.AbstractTest;
import com.example.gptlearn.entity.Role;
import com.example.gptlearn.entity.User;
import com.example.gptlearn.model.dto.enums.RoleE;
import com.example.gptlearn.repository.UserRepository;
import com.example.gptlearn.service.RoleService;
import com.example.gptlearn.service.UserRolesService;
import com.example.gptlearn.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class AdminControllerTests extends AbstractTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRolesService userRolesService;

    private final static String USERNAME = "user_test";

    @BeforeEach
    public void addUser() {
        userService.add(USERNAME, "pass");
    }

    @AfterEach
    public void clearUser() {
        userRepository.deleteAll();
    }

    @Test
    @WithAnonymousUser
    void should_returnForbidden_when_addModerAnonymousUser() throws Exception {
        this.mockMvc.perform(post("/moder").param("username", USERNAME))
                .andDo(print())
                .andExpect(status().isUnauthorized())
        ;
        User user = userService.findByUsername(USERNAME);
        List<Role> roles = user.getRoles();
        assertThat(roles).containsExactlyInAnyOrderElementsOf(List.of(roleService.getRole(RoleE.ROLE_USER.name())));

    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void should_returnForbidden_when_addModerUser() throws Exception {

        this.mockMvc.perform(post("/moder").param("username", USERNAME))
                .andDo(print())
                .andExpect(status().isForbidden())
        ;

        User user = userService.findByUsername(USERNAME);
        List<Role> roles = user.getRoles();
        assertThat(roles).containsExactlyInAnyOrderElementsOf(List.of(roleService.getRole(RoleE.ROLE_USER.name())));

    }

    @Test
    @WithMockUser(username = "user", roles = "MODER")
    void should_returnForbidden_when_addModerModer() throws Exception {

        this.mockMvc.perform(post("/moder").param("username", USERNAME))
                .andDo(print())
                .andExpect(status().isForbidden())
        ;

        User user = userService.findByUsername(USERNAME);
        List<Role> roles = user.getRoles();
        assertThat(roles).containsExactlyInAnyOrderElementsOf(List.of(roleService.getRole(RoleE.ROLE_USER.name())));

    }

    @Test
    @WithMockUser(username = "user", roles = "ADMIN")
    void should_returnOk_when_addModerAdmin() throws Exception {

        this.mockMvc.perform(post("/moder").param("username", USERNAME))
                .andDo(print())
                .andExpect(status().isOk())
        ;

        User user = userService.findByUsername(USERNAME);
        List<Role> roles = user.getRoles();
        assertThat(roles).containsExactlyInAnyOrderElementsOf(List.of(
                roleService.getRole(RoleE.ROLE_USER.name()),
                roleService.getRole(RoleE.ROLE_MODER.name())));

    }

    @Test
    @WithMockUser(username = "user", roles = "ADMIN")
    void should_returnOk_when_deleteModerAdmin() throws Exception {
        userRolesService.addModer(USERNAME);

        this.mockMvc.perform(delete("/moder").param("username", USERNAME))
                .andDo(print())
                .andExpect(status().isOk())
        ;

        User user = userService.findByUsername(USERNAME);
        List<Role> roles = user.getRoles();
        assertThat(roles).containsExactlyInAnyOrderElementsOf(List.of(roleService.getRole(RoleE.ROLE_USER.name())));

    }
}
