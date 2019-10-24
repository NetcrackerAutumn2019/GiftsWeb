package com.nectcracker.studyproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/cabinet")
    public String cabinet() {
        return "cabinet";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/friends")
    public String friends() {
        return "friends";
    }

}