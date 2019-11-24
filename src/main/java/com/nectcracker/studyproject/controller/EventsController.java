package com.nectcracker.studyproject.controller;

import com.google.api.client.json.Json;
import com.google.gson.Gson;
import com.nectcracker.studyproject.domain.Events;
import com.nectcracker.studyproject.domain.User;
import com.nectcracker.studyproject.repos.EventsRepository;
import com.nectcracker.studyproject.repos.UserRepository;
import com.nectcracker.studyproject.service.EventsService;
import com.nectcracker.studyproject.service.InterestsService;
import net.minidev.json.JSONObject;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class EventsController {
    @Autowired
    private EventsService eventsService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EventsRepository eventsRepository;

    public EventsController(EventsService eventsService, UserRepository userRepository) {
        this.eventsService = eventsService;
        this.userRepository = userRepository;
    }

    @PostMapping("/addEvent")
    public String addEvent(@RequestBody String data) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName());
        Gson gson = new Gson();
        Events event = gson.fromJson(data, Events.class);
        event = eventsService.checkEventDuplicate(event);
        event.getEventParticipants().add(user);
        user.getEventsSet().add(event);
        eventsRepository.save(event);
        userRepository.save(user);
        return "redirect:/calendar";
    }

}

