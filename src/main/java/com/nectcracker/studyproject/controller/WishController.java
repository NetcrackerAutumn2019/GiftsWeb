package com.nectcracker.studyproject.controller;

import com.nectcracker.studyproject.domain.User;
import com.nectcracker.studyproject.domain.UserWishes;
import com.nectcracker.studyproject.repos.UserRepository;
import com.nectcracker.studyproject.service.UserWishesService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class WishController {
    private final UserWishesService userWishesService;
    private final UserRepository userRepository;

    public WishController(UserWishesService userWishesService, UserRepository userRepository) {
        this.userWishesService = userWishesService;
        this.userRepository = userRepository;
    }

    @GetMapping("/new_wish")
    public String wish(Map<String, Object> model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName());
        Iterable<UserWishes> list = userWishesService.getUserWishes(user);
        model.put("messages", list);
        return "new_wish";
    }

    @PostMapping("/new_wish")
    public String addWish(@RequestParam String text, Map<String, Object> model) {
        userWishesService.addWish(text);
        return "redirect:/cabinet";
    }
}
