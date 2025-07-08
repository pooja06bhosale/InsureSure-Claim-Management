package com.insuresure.authservice.dtos;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDto {
    //inside dto whatever u want to return in response
    //don't wha to share password
    private Long id;
    private String username;
    private String email;

}
