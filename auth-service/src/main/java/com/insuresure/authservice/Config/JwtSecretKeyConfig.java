package com.insuresure.authservice.Config;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Configuration
public class JwtSecretKeyConfig {

    @Autowired
    private JwtConfigProperties jwtConfigProperties;

    @Bean
    public SecretKey secretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtConfigProperties.getSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
//jwtConfigProperties.getSecretKey() --	Fetches the secret key string from your application.yml config
//Decoders.BASE64.decode(...) --	Decodes the Base64-encoded string into raw bytes
//eys.hmacShaKeyFor(keyBytes)	--
// Creates a SecretKey object using those decoded bytes, for HMAC-SHA signature algorithm (used in JWT