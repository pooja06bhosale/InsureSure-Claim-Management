package com.insuresure.authservice.Exception;

public class PasswordMismatchExp extends RuntimeException{
    public PasswordMismatchExp(String message) {
        super(message);

    }
}
