package com.nectcracker.studyproject.controller;

<<<<<<< HEAD
import com.nectcracker.studyproject.domain.UserWishes;
=======
import com.nectcracker.studyproject.domain.UserInfo;
import com.nectcracker.studyproject.repos.UserInfoRepository;
import com.nectcracker.studyproject.repos.UserRepository;
>>>>>>> Feat : add ui
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
<<<<<<< HEAD
        Iterable<UserWishes> list = userWishesService.getUserWishes();
        model.put("messages", list);
=======
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserInfo currentUserInfo = userInfoRepository.findByUser(userRepository.findByUsername(auth.getName()));
        model.put("info", currentUserInfo);
        userWishesService.showWishes(model);
>>>>>>> Feat : add ui
        return "cabinet";
    }
}
