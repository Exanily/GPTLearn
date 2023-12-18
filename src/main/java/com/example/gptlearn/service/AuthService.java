package com.example.gptlearn.service;

import com.example.gptlearn.exception.LoginNotCorrectException;
import com.example.gptlearn.exception.PasswordMismatchException;
import com.example.gptlearn.exception.UsernameBusyException;
import com.example.gptlearn.model.request.SingInRequest;
import com.example.gptlearn.model.request.SingUpRequest;
import com.example.gptlearn.model.response.SingInResponse;
import com.example.gptlearn.securuty.jwt.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtils jwtTokenUtils;
    private final static String ERROR_LOGIN_PASS = "Логин или пароль не сопадают";
    private final static String ERROR_PASSWORD_MISMATCH = "Пароли не совпадают";
    private final static String ERROR_USERNAME_BUSY = "Имя пользователя занято";

    public void singUp(SingUpRequest singUpRequest) {
        try {
            checkingPasswordMatch(singUpRequest.getPassword(), singUpRequest.getPassword2());
            checkingExistUsername(singUpRequest.getUsername());
        } catch (UsernameNotFoundException e) {
            userService.add(singUpRequest.getUsername(), singUpRequest.getPassword());}
    }

    public SingInResponse singIn(SingInRequest singInRequest) {
        auth(singInRequest.getUsername(), singInRequest.getPassword());
        UserDetails userDetails = userService.loadUserByUsername(singInRequest.getUsername());
        String token = jwtTokenUtils.generateToken(userDetails);

        return SingInResponse.builder().username(userDetails.getUsername()).token(token).build();
    }

    private void checkingPasswordMatch(String pass1, String pass2) {
        if (!pass1.equals(pass2)) {
            throw new PasswordMismatchException(ERROR_PASSWORD_MISMATCH);
        }
    }

    private void checkingExistUsername(String username) {
        userService.loadUserByUsername(username);
        throw new UsernameBusyException(ERROR_USERNAME_BUSY);
    }

    private void auth(String username, String pass) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, pass));
        } catch (AuthenticationException e) {
            throw new LoginNotCorrectException(ERROR_LOGIN_PASS);
        }
    }
}
