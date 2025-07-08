package com.insuresure.authservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.insuresure.authservice.Client.KafkaClient;
import com.insuresure.authservice.Config.JwtConfigProperties;
import com.insuresure.authservice.Exception.AccountSuspendedException;
import com.insuresure.authservice.Exception.PasswordMismatchExp;
import com.insuresure.authservice.Exception.UserAlreadySignedExcp;
import com.insuresure.authservice.Exception.UserNotRegisterExp;
import com.insuresure.authservice.Model.State;
import com.insuresure.authservice.Model.User;
import com.insuresure.authservice.Model.Session;
import com.insuresure.authservice.dtos.EmailDto;
import com.insuresure.authservice.repository.SessionRepo;
import com.insuresure.authservice.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private SessionRepo sessionRepo;

    @Autowired
    private SecretKey secretKey;

    @Autowired
    private KafkaClient kafkaClient;

    @Autowired
    private ObjectMapper objectMapper;//jackson-- convert object to string -- vs--json into object


    //Bcryptpasswordencoder used for password security so no one can decode it
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    //we are creating jwt token for login api -- and that token stored into cookies that genrated token pass by all the api

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtConfigProperties jwtConfigProperties;

    @Autowired
    private TokenService tokenService;



    public User signup(String name, String email, String password) {
 Optional<User> userOptional= userRepository.findByEmailEquals(email);
        if(userOptional.isPresent()) {
            throw new UserAlreadySignedExcp("User already exists");
        }
        User user = new User();
        user.setUsername(name);
        //user.setPassword(password);
        //without storing direct password we use bcryptpassword
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setEmail(email);
        //WE WILL ADD LOGIC TO SEND WELCOME EMAIL AT THIS PLACE
        try {
            EmailDto emailDto = new EmailDto();
            emailDto.setTo(email);
            emailDto.setFrom("pooja06bhosale@gmail.com");
            emailDto.setSubject("Welcome to Scaler");
            emailDto.setBody("Hope you will have , good learning experience");
            kafkaClient.sendMessage("signup", objectMapper.writeValueAsString(emailDto));
        }catch (JsonProcessingException exception) {
            throw new RuntimeException("something went wrong");
        }
        return userRepository.save(user);
}

    public Pair<User,String> login(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmailEquals(email);
        if (userOptional.isEmpty()) {
            throw new UserNotRegisterExp("please sign up first");
        }
        if (!userOptional.get().getState().equals(State.ACTIVE)) {
            throw new AccountSuspendedException("Account is temporarily suspended, Please try after some days");
        }
        String StoredPassword = userOptional.get().getPassword();
        //if(!StoredPassword.equals(password)) {
        if (!bCryptPasswordEncoder.matches(password, StoredPassword)) {
            // bCryptPasswordEncoder.matches(password, StoredPassword) used for encode our password and match that with bcrypt
            // storedpassword-- already encoded -- with signup time
            // password -- we encoded now and match with storedpassword
            throw new PasswordMismatchExp("password mismatch");
        }
        //Token Generation -- after login we need token
//-------------------------------------------------------------------------
        //  jwt token have three parts -- header payload signature
        //payload looks like map -- like key value pair so here we use map
        //--pair [k1,v1] , in spring have inbuild pair class-- pair means keep two values together have diff datatype
//-------------------------------------------------------------------------------
        //  payload thing -- can be anything user validation but not password-- payload key value like pair so use map
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userOptional.get().getId());
        Long nowInMillis = System.currentTimeMillis();
        claims.put("iat", nowInMillis);
        claims.put("exp", nowInMillis + 100000);
        // Here, 100000 is the expiry time in milliseconds relative to nowInMillis.
        claims.put("iss", "authservice##");
   /*     Long nowInMillis = System.currentTimeMillis();
          long expiryDurationInMillis = 30 * 60 * 1000; // 30 minutes
          claims.put("iat", nowInMillis);
          claims.put("exp", nowInMillis + expiryDurationInMillis);
          claims.put("iss", "authservice##");
        *
        * we can set expiry in this way
        * */
        //creating token so used signWith(secretkey)-- and builder().
        String token = Jwts.builder().claims(claims).signWith(secretKey).compact();
        Session session = new Session();
        session.setUser(userOptional.get());
        session.setToken(token);
        session.setState(State.ACTIVE);
       sessionRepo.save(session);

        Pair<User, String> response = Pair.of(userOptional.get(), token);
        return response;
    }
    //as of now we don't have another service for token validation so we right code here only
        //Validate if token is being generated by us
        //Check of Expiry of Token

    public Boolean validateToken(String token,Long userId) {
        Optional<Session> optionalSession = sessionRepo.findByTokenAndUser_Id(token, userId);
        if (optionalSession.isEmpty()) {
            System.out.println("Passed Token is not valid");
            return false;
        }
//JwtParser used for varify the token so used verifyWith(secretKey)
        JwtParser jwtParser = Jwts.parser().verifyWith(secretKey).build();
        Claims claims = jwtParser.parseSignedClaims(token).getPayload();
        Long expiryTime = (Long) claims.get("exp");
        System.out.println("expiry = " + expiryTime);
        System.out.println("current =" + System.currentTimeMillis());
        if (expiryTime < System.currentTimeMillis()) {
            System.out.println("Token has expired");
            return false;
        }
        return true;
    }

        }






