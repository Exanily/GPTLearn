package com.example.gptlearn.service;

import com.example.gptlearn.entity.User;
import com.example.gptlearn.exception.LoginNotCorrectException;
import com.example.gptlearn.exception.PasswordMismatchException;
import com.example.gptlearn.exception.UsernameBusyException;
import com.example.gptlearn.model.request.SingInRequest;
import com.example.gptlearn.model.request.SingUpRequest;
import com.example.gptlearn.model.response.SingInResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserServiceImpl userService;
    private final PasswordEncoder passwordEncoder;
    private final static String ERROR_LOGIN_PASS = "Логин или пароль не сопадают";
    private final static String ERROR_PASSWORD_MISMATCH = "Пароли не совпадают";
    private final static String ERROR_USERNAME_BUSY = "Имя пользователя занято";
    public void singUp(SingUpRequest singUpRequest) {
        try {
            if (!singUpRequest.getPassword().equals(singUpRequest.getPassword2())) {
                throw new PasswordMismatchException(ERROR_PASSWORD_MISMATCH);
            }
            userService.loadUserByUsername(singUpRequest.getUsername());
            throw new UsernameBusyException(ERROR_USERNAME_BUSY);
        } catch (UsernameNotFoundException e) {
            userService.add(singUpRequest);
        }

    }

    public SingInResponse singIn(SingInRequest singInRequest) {
        try {
            User user = userService.loadUserByUsername(singInRequest.getUsername());
            if (!passwordEncoder.matches(singInRequest.getPassword(), user.getPassword())) {
                throw new LoginNotCorrectException(ERROR_LOGIN_PASS);
            }
            return new SingInResponse(user.getUsername(), "token");
        } catch (UsernameNotFoundException e) {
            throw new LoginNotCorrectException(ERROR_LOGIN_PASS);
        }

    }
}
