package com.nectcracker.studyproject.controller;

import com.nectcracker.studyproject.domain.*;
import com.nectcracker.studyproject.repos.InterestsRepository;
import com.nectcracker.studyproject.repos.UserInfoRepository;
import com.nectcracker.studyproject.repos.UserRepository;
import com.nectcracker.studyproject.service.EventsService;
import com.nectcracker.studyproject.service.InterestsService;
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

@Slf4j
@Controller
public class UserPageController {
    private final UserWishesService userWishesService;
    private final UserInfoRepository userInfoRepository;
    private final UserRepository userRepository;
    private final InterestsService interestsService;
    private final EventsService eventsService;

    public UserPageController(UserWishesService userWishesService, UserInfoRepository userInfoRepository,
                              UserRepository userRepository, InterestsService interestsService, EventsService eventsService) {
        this.userWishesService = userWishesService;
        this.userInfoRepository = userInfoRepository;
        this.userRepository = userRepository;
        this.interestsService = interestsService;
        this.eventsService = eventsService;
    }

    @GetMapping("/cabinet")
    public String cabinet(Map<String, Object> model) throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName());
        UserInfo currentUserInfo = userInfoRepository.findByUser(user);
        model.put("info", currentUserInfo);
        Iterable<UserWishes> list = userWishesService.getUserWishes(user);
        model.put("messages", list);
        Iterable<Interests> interests = interestsService.getUserInterests();
        model.put("interests", interests);
        Set<Events> userEvents = eventsService.getUserEvents();
        Iterator events = userEvents.iterator();
        List<JSONObject> data = new ArrayList<>();
        JSONParser parser = new JSONParser();
        while(events.hasNext()) {
            Events event = (Events) events.next();
            String jsonString = event.toString();
            org.json.simple.JSONObject json = (org.json.simple.JSONObject) parser.parse(jsonString);
            data.add(json);
        }
        model.put("eventsData", data);
        return "cabinet";
    }
}
