package com.school.management.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PublicController {

    @GetMapping("/")
    public String home(){
        return "public/index";
    }
    
    @GetMapping("/login")
    public String loginPage(){
        return "public/login";
    }

}
