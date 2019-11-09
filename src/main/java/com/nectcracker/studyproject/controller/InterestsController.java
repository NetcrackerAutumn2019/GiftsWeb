package com.nectcracker.studyproject.controller;

import com.nectcracker.studyproject.service.InterestsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class InterestsController {
    private final InterestsService interestsService;

    public InterestsController(InterestsService interestsService) {
        this.interestsService = interestsService;
    }

    @PostMapping("/interests")
    public String updateUserInterest(@RequestParam String interest) {
        interestsService.updateUserInterests(interest);
        return "redirect:/redactor";
    }
}
