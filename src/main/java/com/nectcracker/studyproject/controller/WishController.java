package com.nectcracker.studyproject.controller;

import com.nectcracker.studyproject.service.UserWishesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class WishController {
    @Autowired
    private UserWishesService userWishesService;

    @GetMapping("/new_wish")
    public String wish(Map<String, Object> model) {
        userWishesService.showWishes(model);
        return "new_wish";
    }

    @PostMapping("/new_wish")
    public String addWish(@RequestParam String text, Map<String, Object> model) {
        userWishesService.addWish(text, model);
        return "redirect:/cabinet";
    }
}
