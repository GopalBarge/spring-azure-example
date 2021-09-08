package com.spring.azure.demo.controller;

import com.spring.azure.demo.dto.AS400Request;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AS400Controller {
    @GetMapping
    public String sayHello(){
        return "Welcome to spring azure";
    }

    @PostMapping("/as400")
    public ResponseEntity processAS400Data(@RequestBody List<AS400Request> requestList){

        return new ResponseEntity(HttpStatus.OK);
    }

}
