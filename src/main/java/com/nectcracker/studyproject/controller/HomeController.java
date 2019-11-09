package com.nectcracker.studyproject.controller;

import com.nectcracker.studyproject.service.CalendarService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.nectcracker.studyproject.service.CalendarService.*;


@Controller
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

}
