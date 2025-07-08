package com.insuresure.authservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignupRequestDto {
    //frontend data

    private String username;
    private String email;
    private String password;
}
