package com.example.placement.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController  //this class handles HTTP requests
public class TestController {

    @GetMapping("/test")    //maps URL request - is someone vists GET/test - calls test()
    public String test(){
        return "Backend is running";
    }
}
