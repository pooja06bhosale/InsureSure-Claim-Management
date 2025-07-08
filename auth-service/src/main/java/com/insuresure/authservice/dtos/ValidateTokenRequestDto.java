package com.insuresure.authservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ValidateTokenRequestDto {
    private Long userId;
    private String token;
}