package com.spring.azure.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AS400Controller {
    @GetMapping
    public String sayHello(){
        return "Welcome to spring azure";
    }
}
