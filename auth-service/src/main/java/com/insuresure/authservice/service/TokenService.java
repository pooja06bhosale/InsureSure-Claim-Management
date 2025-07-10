package com.insuresure.authservice.service;


import com.insuresure.authservice.Config.JwtConfigProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TokenService {

    @Autowired
    private SecretKey secretKey;

    @Autowired
    private JwtConfigProperties jwtConfigProperties; // for secretkey

    // Generic token generator
    // Create a JWT for a given user with a configurable expiry time in milliseconds.

    public String generateToken(Long userId, long expiryDurationMillis) {
        Long nowInMillis = System.currentTimeMillis(); // // current time in milliseconds
        Date now = new Date(nowInMillis);   // current Date object
        Date expiryDate = new Date(nowInMillis + expiryDurationMillis); // token expiration time (current time + expiry duration)

        Map<String, Object> claims = new HashMap<>(); // payload present in key value pair
        claims.put("userId", userId);//claims: extra data you want to embed in the token payload (here, just the userId).

        return Jwts.builder()
                .claims(claims)//puts your data in token payload.
                .issuedAt(now) // when the token was generated.
                .expiration(expiryDate) //when the token will expire.
                .signWith(secretKey) // signs the token with your configured secretKey to make it secure.
                .compact(); // converts it into a string JWT
    }



    // Generic token validation
    public boolean validateToken(String token) {
        try {
            //Creates a JwtParser using your secretKey to decode and verify the token’s signature.
            JwtParser jwtParser = Jwts.parser().verifyWith(secretKey).build();
            Claims claims = jwtParser.parseSignedClaims(token).getPayload();//Parses the token and extracts the payload (claims).

            Date expiryDate = claims.getExpiration();
            return expiryDate.after(new Date());//Checks whether the expiryDate inside the token is after the current time.
                                                // If yes → token is valid
                                                // If expired → returns false

        } catch (Exception e) {
            System.out.println("Invalid token: " + e.getMessage());
            return false;
        }
    }
}
//In JWT (JSON Web Token), the payload part contains information in the form of claims.