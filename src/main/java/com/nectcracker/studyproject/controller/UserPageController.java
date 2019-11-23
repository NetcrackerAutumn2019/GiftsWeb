package com.nectcracker.studyproject.controller;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.nectcracker.studyproject.domain.*;
import com.nectcracker.studyproject.repos.InterestsRepository;
import com.nectcracker.studyproject.repos.UserInfoRepository;
import com.nectcracker.studyproject.repos.UserRepository;
import com.nectcracker.studyproject.service.EventsService;
import com.nectcracker.studyproject.service.InterestsService;
import com.nectcracker.studyproject.service.UserService;
import com.nectcracker.studyproject.service.UserWishesService;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Slf4j
@Controller
public class UserPageController {
    private final UserWishesService userWishesService;
    private final UserInfoRepository userInfoRepository;
    private final UserRepository userRepository;
    private final InterestsService interestsService;
    private final EventsService eventsService;
    private final UserService userService;

    private CacheLoader<User, Map> loader = new CacheLoader<User, Map>() {
        @Override
        public Map load(User user) throws Exception {
            return userService.takeFriendFromVk(user);
        }
    };
    private LoadingCache<User, Map> cache = CacheBuilder.newBuilder().refreshAfterWrite(30, TimeUnit.MINUTES).build(loader);;

    public UserPageController(UserWishesService userWishesService, UserInfoRepository userInfoRepository,
                              UserRepository userRepository,
                              InterestsService interestsService,
                              EventsService eventsService, UserService userService) {
        this.userWishesService = userWishesService;
        this.userInfoRepository = userInfoRepository;
        this.userRepository = userRepository;
        this.interestsService = interestsService;
        this.eventsService = eventsService;
        this.userService = userService;
    }

    @GetMapping("/cabinet")
    public String cabinet(Map<String, Object> model) throws ParseException, ExecutionException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName());
        UserInfo currentUserInfo = userInfoRepository.findByUser(user);
        model.put("info", currentUserInfo);
        Iterable<UserWishes> list = userWishesService.getUserWishes(user);
        model.put("messages", list);
        Iterable<Interests> interests = interestsService.getUserInterests();
        model.put("interests", interests);
        List<org.json.simple.JSONObject> currentUserData = eventsService.getUserEvents(user);

        Map<String, Set> friendsMapForForm = cache.get(user);
        Set<User> friends = friendsMapForForm.get("registered");
        Iterator friendsIterator = friends.iterator();
        List<org.json.simple.JSONObject> friendsEventsData = new ArrayList<>();

        while(friendsIterator.hasNext()) {
            User friend = (User) friendsIterator.next();
            List<org.json.simple.JSONObject> friendEvents = eventsService.getUserEvents(friend);
            friendsEventsData.addAll(friendEvents);
        }


        model.put("eventsData", currentUserData);
        model.put("friendsEventsData", friendsEventsData);
        return "cabinet";
    }
}
