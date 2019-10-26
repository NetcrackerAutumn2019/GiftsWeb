package com.nectcracker.studyproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/new_wish")
@Controller
public class WishController {
    @GetMapping("")
    public String new_wish() {
        return "new_wish";
    }
}
