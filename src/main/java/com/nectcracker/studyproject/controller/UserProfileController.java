package com.nectcracker.studyproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserProfileController {
    @GetMapping("/redactor")
    public String redactor() {
        return "redactor";
    }
}
