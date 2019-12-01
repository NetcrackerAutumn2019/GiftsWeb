package com.nectcracker.studyproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nectcracker.studyproject.domain.Events;
import com.nectcracker.studyproject.domain.User;
import com.nectcracker.studyproject.repos.EventsRepository;
import com.nectcracker.studyproject.repos.UserRepository;
import com.nectcracker.studyproject.service.EventsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


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
    public String addEvent(@RequestBody String data) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName());
        ObjectMapper mapper = new ObjectMapper();
        Events event = mapper.readValue(data, Events.class);
        event = eventsService.checkEventDuplicate(event);
        event.getEventParticipants().add(user);
        user.getEventsSet().add(event);
        eventsRepository.save(event);
        userRepository.save(user);
        return "forward:/cabinet";
    }

}

