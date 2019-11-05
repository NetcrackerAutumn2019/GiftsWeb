package com.nectcracker.studyproject.controller;

import com.nectcracker.studyproject.domain.Interests;
import com.nectcracker.studyproject.domain.User;
import com.nectcracker.studyproject.domain.UserInfo;
import com.nectcracker.studyproject.domain.UserWishes;
import com.nectcracker.studyproject.repos.UserInfoRepository;
import com.nectcracker.studyproject.repos.UserRepository;
import com.nectcracker.studyproject.service.InterestsService;
import com.nectcracker.studyproject.service.UserWishesService;
import com.nectcracker.studyproject.service.VkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RequestMapping()
@Controller
public class FriendsController {

    private final UserRepository userRepository;
    private final InterestsService interestsService;
    private final UserWishesService userWishesService;
    private final UserInfoRepository userInfoRepository;

    public FriendsController(UserRepository userRepository, InterestsService interestsService,
                             UserWishesService userWishesService, UserInfoRepository userInfoRepository) {
        this.userRepository = userRepository;
        this.interestsService = interestsService;
        this.userWishesService = userWishesService;
        this.userInfoRepository = userInfoRepository;
    }

    @GetMapping("/friends")
    public String friends(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName());
        model.addAttribute("friends", user.getFriends());
        return "friends";
    }

    @PostMapping("/friend_page/{name}")
    public String friendPage(@PathVariable String name, Map<String, Object> model) {
        User friend = userRepository.findByUsername(name);
        UserInfo currentUserInfo = userInfoRepository.findByUser(userRepository.findByUsername(name));
        model.put("info", currentUserInfo);
        Iterable<Interests> list = interestsService.getSmbInterests(friend);
        model.put("interests", list);
        Iterable<UserWishes> wishes = userWishesService.getUserWishes(friend);
        model.put("messages", wishes);
        return "friend_page";
    }

}
