package com.insuresure.authservice.controller;


import com.insuresure.authservice.Model.User;
import com.insuresure.authservice.dtos.UserDto;
import com.insuresure.authservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
    @RequestMapping("/users")
    public class UserController {

        @Autowired
        private UserService userService;

        @GetMapping("/{id}")
        public UserDto getUserDetails(@PathVariable Long id) {
            User user = userService.getUserDetail(id);
            if(user == null) return null;
            return from(user);
        }


    @Autowired// communicatin with paymentservice
    private RestTemplate restTemplate;

    @GetMapping("/call-payment")
    public String callPaymentService() {
        String response = restTemplate.getForObject("http://PAYMENTSERVICEJAN25/payments", String.class);
        return response;
    }

        private UserDto from(User user) {
            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setUsername(user.getUsername());
            userDto.setEmail(user.getEmail());
            return userDto;
        }

    }
