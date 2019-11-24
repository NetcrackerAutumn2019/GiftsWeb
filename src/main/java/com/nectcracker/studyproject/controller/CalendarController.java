package com.nectcracker.studyproject.controller;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.nectcracker.studyproject.domain.User;
import com.nectcracker.studyproject.repos.EventsRepository;
import com.nectcracker.studyproject.repos.UserRepository;
import com.nectcracker.studyproject.service.EventsService;
import com.nectcracker.studyproject.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Slf4j
@Controller
public class CalendarController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EventsRepository eventsRepository;
    @Autowired
    private EventsService eventsService;
    @Autowired
    private UserService userService;

    private CacheLoader<User, Map> loader = new CacheLoader<User, Map>() {
        @Override
        public Map load(User user) throws Exception {
            return userService.takeFriendFromVk(user);
        }
    };
    private LoadingCache<User, Map> cache = CacheBuilder.newBuilder().refreshAfterWrite(30, TimeUnit.MINUTES).build(loader);;


    @GetMapping("/calendar")
    public String calendar(Map<String, Object> model) throws ParseException, ExecutionException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) userService.loadUserByUsername(auth.getName());
        List<org.json.simple.JSONObject> currentUserData = eventsService.getUserEvents(currentUser);

        Map<String, Set> friendsMapForForm = cache.get(currentUser);
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
        return "calendar";

    }


}






