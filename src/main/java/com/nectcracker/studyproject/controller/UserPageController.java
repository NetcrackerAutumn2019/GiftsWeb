package com.nectcracker.studyproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserPageController {
    @GetMapping("/cabinet")
    public String cabinet() {
        return "cabinet";
    }
}
