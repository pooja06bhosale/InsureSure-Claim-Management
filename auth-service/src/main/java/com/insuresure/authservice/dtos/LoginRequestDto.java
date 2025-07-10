package com.insuresure.authservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequestDto {
    // sending and receive  data from client as per need
    private String email;
    private String password;
}
