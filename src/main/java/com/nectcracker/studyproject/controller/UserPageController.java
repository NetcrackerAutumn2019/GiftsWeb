package com.nectcracker.studyproject.controller;

import com.nectcracker.studyproject.domain.UserInfo;
import com.nectcracker.studyproject.domain.UserWishes;
import com.nectcracker.studyproject.repos.UserInfoRepository;
import com.nectcracker.studyproject.repos.UserRepository;
import com.nectcracker.studyproject.service.UserWishesService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class UserPageController {
    private final UserWishesService userWishesService;

    private final UserInfoRepository userInfoRepository;

    private final UserRepository userRepository;

    public UserPageController(UserWishesService userWishesService, UserInfoRepository userInfoRepository, UserRepository userRepository) {
        this.userWishesService = userWishesService;
        this.userInfoRepository = userInfoRepository;
        this.userRepository = userRepository;
    }

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
