package com.insuresure.authservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmailDto {
    //wants to communicate with email service via kafka
    private String to;
    private String from;
    private String subject;
    private String body;
}