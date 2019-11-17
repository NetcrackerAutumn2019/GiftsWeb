package com.nectcracker.studyproject.controller;

import com.google.gson.Gson;
import com.nectcracker.studyproject.domain.Events;
import com.nectcracker.studyproject.domain.User;
import com.nectcracker.studyproject.repos.EventsRepository;
import com.nectcracker.studyproject.repos.UserRepository;
import com.nectcracker.studyproject.service.EventsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@Slf4j
@Controller
public class CalendarController {

    private UserRepository userRepository;
    private EventsRepository eventsRepository;
    private EventsService eventsService;


    @GetMapping("/calendar")
    public String calendar(Map<String, Object> model) {
            Set<Events> userEvents = eventsService.getUserEvents();
            Iterator events = userEvents.iterator();
            List<String> data = new ArrayList<>();
            Gson gson = new Gson();
            while(events.hasNext()) {
                Events event = (Events) events.next();
                String json = gson.toJson(event);
                data.add(json);
            }
            model.put("eventsData", data);
            return "calendar";

    }


}





