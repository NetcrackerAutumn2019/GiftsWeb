package com.nectcracker.studyproject.controller;

import com.nectcracker.studyproject.domain.UserInfo;
import com.nectcracker.studyproject.domain.UserWishes;
import com.nectcracker.studyproject.repos.UserInfoRepository;
import com.nectcracker.studyproject.repos.UserRepository;
import com.nectcracker.studyproject.service.UserWishesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class UserPageController {
    @Autowired
    private UserWishesService userWishesService;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/cabinet")
    public String cabinet(Map<String, Object> model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserInfo currentUserInfo = userInfoRepository.findByUser(userRepository.findByUsername(auth.getName()));
        model.put("info", currentUserInfo);
        Iterable<UserWishes> list = userWishesService.getUserWishes();
        model.put("messages", list);
        return "cabinet";
    }
}
