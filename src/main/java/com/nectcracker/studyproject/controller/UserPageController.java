package com.nectcracker.studyproject.controller;

import com.nectcracker.studyproject.domain.UserWishes;
import com.nectcracker.studyproject.service.UserWishesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class UserPageController {
    @Autowired
    private UserWishesService userWishesService;

    @GetMapping("/cabinet")
    public String cabinet(Map<String, Object> model) {
        Iterable<UserWishes> list = userWishesService.getUserWishes();
        model.put("messages", list);
        return "cabinet";
    }
}
