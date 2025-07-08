package com.insuresure.authservice.Model;


import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Session extends BaseModel {

    // session used for storing and retriving data from db
    //for storing token into database auth server
private String token;
@ManyToOne
private User user;

}

//1           1
//session    user
//m            1
//
//
//m : 1
