package com.example.gptlearn.handler;

import com.example.gptlearn.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
public class ErrorControllerHandler {

    @ExceptionHandler({UsernameBusyException.class,
            PasswordMismatchException.class,
            LoginNotCorrectException.class,
            TaskNotFoundException.class,
            ThemeDuplicateException.class,
            ThemeNotFoundException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> responseBadRequest(RuntimeException exception) {
        return buildResponse(HttpStatus.BAD_REQUEST, exception);
    }

    @ExceptionHandler({ChatGPTResponseException.class,
            RoleNotFoundException.class
    })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> responseEntity(RuntimeException exception) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception);
    }

    private ResponseEntity<ErrorResponse> buildResponse(HttpStatus status, RuntimeException exception) {
        return ResponseEntity.status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ErrorResponse.builder()
                        .error(exception.getMessage())
                        .date(new Date())
                        .build());
    }
}
