package com.nectcracker.studyproject.controller;

import com.google.gson.Gson;
import com.nectcracker.studyproject.domain.Events;
import com.nectcracker.studyproject.domain.User;
import com.nectcracker.studyproject.repos.EventsRepository;
import com.nectcracker.studyproject.repos.UserRepository;
import com.nectcracker.studyproject.service.EventsService;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.json.Json;
import java.io.IOException;
import java.util.*;

@Slf4j
@Controller
public class CalendarController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EventsRepository eventsRepository;
    @Autowired
    private EventsService eventsService;


    @GetMapping("/calendar")
    public String calendar(Map<String, Object> model) throws IOException, ParseException {
            Set<Events> userEvents = eventsService.getUserEvents();
            Iterator events = userEvents.iterator();
            List<org.json.simple.JSONObject> data = new ArrayList<>();
            JSONParser parser = new JSONParser();
            while(events.hasNext()) {
                Events event = (Events) events.next();
                String jsonString = event.toString();
                org.json.simple.JSONObject json = (org.json.simple.JSONObject) parser.parse(jsonString);
                data.add(json);
            }
            model.put("eventsData", data);
            return "calendar";

    }


}





