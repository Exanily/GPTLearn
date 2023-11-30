package com.example.gptlearn.service;

import com.example.gptlearn.entity.Role;
import com.example.gptlearn.exception.RoleNotFoundException;
import com.example.gptlearn.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class RoleService {

    private final RoleRepository roleRepository;
    private final static String ROLE_NOT_FOUND = "Роль не найдена";

    public Role getRole(String name) {
        return roleRepository.findByName(name).orElseThrow(() -> {
            String error = ROLE_NOT_FOUND + ": " + name;
            log.error(error);
            return new RoleNotFoundException(error);
        });
    }
}
