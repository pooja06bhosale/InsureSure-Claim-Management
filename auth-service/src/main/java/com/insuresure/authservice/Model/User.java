package com.insuresure.authservice.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;


@Setter
@Getter
@Entity
public class User extends BaseModel{

    private String username;
    private String password;
    private String email;

    public User() {
        this.setCreatedAt(new Date());
        this.setLastupdatedAt(new Date());
        this.setState(State.ACTIVE);
    }
}
