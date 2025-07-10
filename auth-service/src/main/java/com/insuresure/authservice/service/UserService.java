package com.insuresure.authservice.service;


import com.insuresure.authservice.Model.User;
import com.insuresure.authservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getUserDetail(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        //optional handles null pointer exception
        if(userOptional.isEmpty()) {
            System.out.println("NO USER FOUND");
            return null;
        }

        System.out.println(userOptional.get().getEmail());
        return userOptional.get();
    }
}
