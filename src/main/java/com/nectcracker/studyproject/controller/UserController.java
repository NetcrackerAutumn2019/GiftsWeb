package com.nectcracker.studyproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping("/redactor")
    public String redactor() {
        return "redactor";
    }

    @GetMapping("/cabinet")
    public String cabinet(){
        return "cabinet";
    }

    @GetMapping("/friends")
    public String friends() {
        return "friends";
    }
}
