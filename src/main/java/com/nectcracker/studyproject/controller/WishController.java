package com.nectcracker.studyproject.controller;

import com.nectcracker.studyproject.domain.UserWishes;
import com.nectcracker.studyproject.service.UserWishesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class WishController {
    private final UserWishesService userWishesService;

    public WishController(UserWishesService userWishesService) {
        this.userWishesService = userWishesService;
    }

    @GetMapping("/new_wish")
    public String wish(Map<String, Object> model) {
        Iterable<UserWishes> list = userWishesService.getUserWishes();
        model.put("messages", list);
        return "new_wish";
    }

    @PostMapping("/new_wish")
    public String addWish(@RequestParam String text, Map<String, Object> model) {
        userWishesService.addWish(text);
        return "redirect:/cabinet";
    }
}
