package com.example.gptlearn.service;

import com.example.gptlearn.entity.User;
import com.example.gptlearn.repository.UserRepository;
import com.example.gptlearn.model.request.SingUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(""));
    }

    public User add(SingUpRequest singUpRequest) {
        User user = new User();
        user.setUsername(singUpRequest.getUsername());
        user.setPassword(passwordEncoder.encode(singUpRequest.getPassword()));
        user.setRegistrationDate(new Date());
        return userRepository.save(user);
    }
}
