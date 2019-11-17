package com.nectcracker.studyproject.controller;

import com.nectcracker.studyproject.domain.User;
import com.nectcracker.studyproject.repos.UserRepository;
import com.nectcracker.studyproject.service.EventsService;
import com.nectcracker.studyproject.service.InterestsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Controller
public class EventsController {
    @Autowired
    private EventsService eventsService;
    private UserRepository userRepository;

    public EventsController(EventsService eventsService, UserRepository userRepository) {
        this.eventsService = eventsService;
        this.userRepository = userRepository;
    }

    @PostMapping("/addEvent")
    public String addEvent(@RequestParam Map<String,String> data) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName());
        String title = data.get("title");
        String start = data.get("start");
        eventsService.createEvent(title, start, user, true);
        return "redirect:/calendar";
    }

}

