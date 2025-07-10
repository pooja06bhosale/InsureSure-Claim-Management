package com.insuresure.authservice.Config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestTemplate;

import javax.crypto.SecretKey;

@Configuration

public class AuthConfig {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean // jwt header -- HS256 or RSA -- for password security
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors().disable()
                .csrf().disable()
                .authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll())
                .formLogin().disable()
                .httpBasic().disable();
        return httpSecurity.build();
    }


    // Explicitly define AuthenticationManager to prevent auto in-memory config
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }


    //cors =  CROSS ORIGIN (Domain) RESOURCE (IMG/VIDEO) SHARING
    //cors means you're using resources from other domain(scaler, greeks for greeks0
//     httpSecurity.authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll());-- give permission for all req

    /*
    * CORS → for allowing cross-origin calls

CSRF → because we're developing a stateless REST API using JWT

Default login form by configuring .authorizeHttpRequests() to allow public access to login, signup, etc.*/



}
