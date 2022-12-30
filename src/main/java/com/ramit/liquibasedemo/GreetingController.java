package com.ramit.liquibasedemo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    @GetMapping("/")
    public String greet(){
        return "Welcome to liquibase POC";
    }
}
