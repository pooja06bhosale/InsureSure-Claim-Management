package com.insuresure.authservice.controller;

import com.insuresure.authservice.Exception.PasswordMismatchExp;
import com.insuresure.authservice.Exception.UserNotRegisterExp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler( {PasswordMismatchExp.class,  UserNotRegisterExp.class, UserNotRegisterExp.class})
    public ResponseEntity<String> handleException(Exception exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
