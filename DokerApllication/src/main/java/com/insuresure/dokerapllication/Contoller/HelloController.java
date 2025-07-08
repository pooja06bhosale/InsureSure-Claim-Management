package com.insuresure.dokerapllication.Contoller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/{key}")
    public String welcome(@PathVariable("key") String key) {
        return "Hello "+key;
    }}