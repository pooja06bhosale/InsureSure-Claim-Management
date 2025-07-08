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
    private JwtConfigProperties jwtConfigProperties;

    // Generic token generator
    public String generateToken(Long userId, long expiryDurationMillis) {
        Long nowInMillis = System.currentTimeMillis();
        Date now = new Date(nowInMillis);
        Date expiryDate = new Date(nowInMillis + expiryDurationMillis);

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);

        return Jwts.builder()
                .claims(claims)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(secretKey)
                .compact();
    }



    // Generic token validation
    public boolean validateToken(String token) {
        try {
            JwtParser jwtParser = Jwts.parser().verifyWith(secretKey).build();
            Claims claims = jwtParser.parseSignedClaims(token).getPayload();

            Date expiryDate = claims.getExpiration();
            return expiryDate.after(new Date());

        } catch (Exception e) {
            System.out.println("Invalid token: " + e.getMessage());
            return false;
        }
    }
}
