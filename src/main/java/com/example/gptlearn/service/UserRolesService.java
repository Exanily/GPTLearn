package com.example.gptlearn.service;

import com.example.gptlearn.entity.Role;
import com.example.gptlearn.entity.User;
import com.example.gptlearn.model.dto.enums.RoleE;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserRolesService {

    private final UserService userService;
    private final RoleService roleService;

    public void addModer(String username) {
        User user = userService.findByUsername(username);
        addModer(user);
    }

    public void addModer(User user) {
        Role moder = roleService.getRole(RoleE.ROLE_MODER.name());
        add(user, moder);
    }

    public void add(User user, Role role) {
        user.getRoles().add(role);
    }

    public void deleteModer(String username) {
        User user = userService.findByUsername(username);
        deleteModer(user);
    }
    public void deleteModer(User user) {
        Role moder = roleService.getRole(RoleE.ROLE_MODER.name());
        delete(user, moder);
    }

    public void delete(User user, Role role) {
        user.getRoles().remove(role);
    }
}
