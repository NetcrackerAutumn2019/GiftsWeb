package com.nectcracker.studyproject.controller;

import com.nectcracker.studyproject.domain.Interests;
import com.nectcracker.studyproject.domain.User;
import com.nectcracker.studyproject.domain.UserInfo;
import com.nectcracker.studyproject.domain.UserWishes;
import com.nectcracker.studyproject.repos.UserInfoRepository;
import com.nectcracker.studyproject.repos.UserRepository;
import com.nectcracker.studyproject.service.InterestsService;
import com.nectcracker.studyproject.service.UserService;
import com.nectcracker.studyproject.service.UserWishesService;
import com.nectcracker.studyproject.service.VkService;
import org.bouncycastle.math.raw.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.jws.WebParam;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

@RequestMapping()
@Controller
public class FriendsController {

    private final UserService userService;
    private final InterestsService interestsService;
    private final UserWishesService userWishesService;
    private final UserInfoRepository userInfoRepository;

    public FriendsController(UserService userService, InterestsService interestsService,
                             UserWishesService userWishesService, UserInfoRepository userInfoRepository) {
        this.userService = userService;
        this.interestsService = interestsService;
        this.userWishesService = userWishesService;
        this.userInfoRepository = userInfoRepository;
    }

    @GetMapping("/friends")
    public String friends(Model model) throws InterruptedException, ExecutionException, IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) userService.loadUserByUsername(auth.getName());

        Map<String, Set> friendsMapForForm = userService.takeFriendFromVk(user);
        model.addAttribute("registeredFriends", friendsMapForForm.get("registered"));
        model.addAttribute("notRegisteredFriends", friendsMapForForm.get("notRegistered"));
        return "friends";
    }

    @PostMapping("friend_send_invite/{vkId}")
    public void sendInvite(@PathVariable Long vkId, Model model){

    }

    @PostMapping("/friend_page/{name}")
    public String friendPage(@PathVariable String name, Map<String, Object> model) {
        User friend = (User) userService.loadUserByUsername(name);
        UserInfo currentUserInfo = userInfoRepository.findByUser(friend);
        model.put("info", currentUserInfo);
        Iterable<Interests> list = interestsService.getSmbInterests(friend);
        model.put("interests", list);
        Iterable<UserWishes> wishes = userWishesService.getUserWishes(friend);
        model.put("messages", wishes);
        return "friend_page";
    }

}
