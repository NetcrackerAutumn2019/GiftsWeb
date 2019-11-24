package com.nectcracker.studyproject.controller;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.nectcracker.studyproject.domain.Interests;
import com.nectcracker.studyproject.domain.User;
import com.nectcracker.studyproject.domain.UserInfo;
import com.nectcracker.studyproject.domain.UserWishes;
import com.nectcracker.studyproject.repos.UserInfoRepository;
import com.nectcracker.studyproject.service.EventsService;
import com.nectcracker.studyproject.service.InterestsService;
import com.nectcracker.studyproject.service.UserService;
import com.nectcracker.studyproject.service.UserWishesService;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import com.sun.media.sound.MidiOutDeviceProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@RequestMapping()
@Controller
public class FriendsController {

    private final UserService userService;
    private final InterestsService interestsService;
    private final UserWishesService userWishesService;
    private final UserInfoRepository userInfoRepository;
    private final EventsService eventsService;

    private CacheLoader<User, Map> loader = new CacheLoader<User, Map>() {
        @Override
        public Map load(User user) throws Exception {
            return userService.takeFriendFromVk(user);
        }
    };
    private LoadingCache<User, Map> cache = CacheBuilder.newBuilder().refreshAfterWrite(30, TimeUnit.MINUTES).build(loader);;

    public FriendsController(UserService userService, InterestsService interestsService,
                             UserWishesService userWishesService, UserInfoRepository userInfoRepository, EventsService eventsService) {
        this.userService = userService;
        this.interestsService = interestsService;
        this.userWishesService = userWishesService;
        this.userInfoRepository = userInfoRepository;
        this.eventsService = eventsService;
    }

    @GetMapping("/friends")
    public String friends(Model model) throws ExecutionException{
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) userService.loadUserByUsername(auth.getName());

        Map<String, Set> friendsMapForForm = cache.get(user);

        model.addAttribute("registeredFriends", friendsMapForForm.get("registered"));
        model.addAttribute("notRegisteredFriends", friendsMapForForm.get("notRegistered"));
        return "friends";
    }

    @RequestMapping("/friend_page/{name}")
    public String friendPage(@PathVariable String name, Map<String, Object> model) throws ParseException {
        model.put("name", name);
        User friend = (User) userService.loadUserByUsername(name);
        UserInfo currentUserInfo = userInfoRepository.findByUser(friend);
        List<JSONObject> friendEventsData = eventsService.getUserEvents(friend);
        model.put("eventsData", friendEventsData);
        model.put("info", currentUserInfo);
        Iterable<Interests> list = interestsService.getSmbInterests(friend);
        model.put("interests", list);
        Iterable<UserWishes> wishes = userWishesService.getUserWishes(friend);
        Set<UserWishes> friendWishes = new HashSet<>();
        Set<UserWishes> userWishes = new HashSet<>();
        for(UserWishes wish : wishes){
            if(wish.isFriendCreateWish())
                friendWishes.add(wish);
            else
                userWishes.add(wish);
        }
        model.put("friendWishes", friendWishes);
        model.put("userWishes", userWishes);

        return "friend_page";
    }

}
