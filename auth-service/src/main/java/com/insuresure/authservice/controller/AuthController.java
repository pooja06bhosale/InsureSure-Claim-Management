package com.insuresure.authservice.controller;

import com.insuresure.authservice.Exception.PasswordMismatchExp;
import com.insuresure.authservice.Exception.UserAlreadySignedExcp;
import com.insuresure.authservice.Exception.UserNotRegisterExp;
import com.insuresure.authservice.Model.User;
import com.insuresure.authservice.dtos.LoginRequestDto;
import com.insuresure.authservice.dtos.SignupRequestDto;
import com.insuresure.authservice.dtos.UserDto;
import com.insuresure.authservice.dtos.ValidateTokenRequestDto;
import com.insuresure.authservice.service.AuthService;
import com.insuresure.authservice.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

@Autowired
private AuthService authService;

@Autowired
private TokenService tokenService;


    @PostMapping("/signup")
    public UserDto signup(@RequestBody SignupRequestDto signupRequestDto) {
        User user = authService.signup(signupRequestDto.getUsername(), signupRequestDto.getEmail(),signupRequestDto.getPassword());
       return from(user);
        // RequestBody mapping data from postman to SignupRequestDto filed and give response
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        Pair<User,String> userWithToken  = authService.login(loginRequestDto.getEmail(),  loginRequestDto.getPassword());
        UserDto userDto = from(userWithToken.getFirst()); // get user object and stored into userdto
        String token = userWithToken.getSecond(); // getting token
        MultiValueMap<String,String> headers = new LinkedMultiValueMap<>();
        headers.add(HttpHeaders.SET_COOKIE,token); // token add to the cookies
        return new ResponseEntity<>(userDto,headers,HttpStatus.OK);
    }

    @PostMapping("/validateToken")
    public Boolean validateToken(@RequestBody ValidateTokenRequestDto validateTokenRequestDto) {
        return authService.validateToken(validateTokenRequestDto.getToken(), validateTokenRequestDto.getUserId());
    }

   /*

   @PostMapping("/validateToken")
    public ResponseEntity<Boolean> validateToken(@RequestBody ValidateTokenRequestDto validateTokenRequestDto) {
        boolean isValid = tokenService.validateToken(validateTokenRequestDto.getToken());
        return ResponseEntity.ok(isValid);
    }

    */



    private UserDto from(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        return userDto;
    }


}


