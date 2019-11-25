package com.nectcracker.studyproject.controller;

import com.nectcracker.studyproject.domain.Interests;
import com.nectcracker.studyproject.domain.User;
import com.nectcracker.studyproject.domain.UserInfo;
import com.nectcracker.studyproject.domain.UserWishes;
import com.nectcracker.studyproject.repos.InterestsRepository;
import com.nectcracker.studyproject.repos.UserInfoRepository;
import com.nectcracker.studyproject.repos.UserRepository;
import com.nectcracker.studyproject.service.InterestsService;
import com.nectcracker.studyproject.service.NewsService;
import com.nectcracker.studyproject.service.UserWishesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Slf4j
@Controller
public class UserPageController {
    private final UserWishesService userWishesService;
    private final UserInfoRepository userInfoRepository;
    private final UserRepository userRepository;
    private final InterestsService interestsService;
    private final NewsService newsService;

    public UserPageController(UserWishesService userWishesService, UserInfoRepository userInfoRepository,
                              UserRepository userRepository, InterestsService interestsService,
                              NewsService newsService) {
        this.userWishesService = userWishesService;
        this.userInfoRepository = userInfoRepository;
        this.userRepository = userRepository;
        this.interestsService = interestsService;
        this.newsService = newsService;
    }

    @GetMapping("/cabinet")
    public String cabinet(Map<String, Object> model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName());
        UserInfo currentUserInfo = userInfoRepository.findByUser(user);
        model.put("info", currentUserInfo);
        Iterable<UserWishes> list = userWishesService.getUserWishes(user);
        model.put("wishes", list);
        Iterable<Interests> interests = interestsService.getUserInterests();
        model.put("interests", interests);
        model.put("newsNumber", newsService.findByUser(user).get("newNews").size());
        return "cabinet";
    }
}
