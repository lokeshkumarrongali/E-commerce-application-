package com.example.SpringEcom.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class HelloController {
    @GetMapping("/greet")
    public String greet(){
        return "Hello baby work hard i love you so much 🫦🫦";
    }
}
