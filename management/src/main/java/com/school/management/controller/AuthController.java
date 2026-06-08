package com.school.management.controller;


import org.springframework.security.core.Authentication;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/dashboard")
    public String dashboardRedirect(
            Authentication authentication
    ) {

        String role = authentication
                .getAuthorities()
                .iterator()
                .next()
                .getAuthority();

        if(role.equals("ROLE_ADMIN")) {
            return "redirect:/admin/dashboard";
        }

        if(role.equals("ROLE_TEACHER")) {
            return "redirect:/teacher/dashboard";
        }

        return "redirect:/student/dashboard";
    }
}