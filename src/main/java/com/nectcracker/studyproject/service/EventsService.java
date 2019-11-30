package com.nectcracker.studyproject.service;

import com.nectcracker.studyproject.domain.Events;
import com.nectcracker.studyproject.domain.User;
import com.nectcracker.studyproject.domain.UserInfo;
import com.nectcracker.studyproject.repos.EventsRepository;
import com.nectcracker.studyproject.repos.UserInfoRepository;
import com.nectcracker.studyproject.repos.UserRepository;
import com.nectcracker.studyproject.repos.UserRepositoryCustom;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
public class EventsService implements UserRepositoryCustom {
    @Autowired
    private EventsRepository eventsRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public User findByAuthentication() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) userService.loadUserByUsername(auth.getName());
        return user;
    }

    public Set<Events> getUserEvents(User user)  {
        Set<Events> events = user.getEventsSet();
        return events;
    }

    public void createEvent (String title, String start, User user, Boolean allDay){
        Events event = new Events(title, start, allDay);
        event.getEventParticipants().add(user);
        eventsRepository.save(event);
    }

    public Events checkEventDuplicate(Events event){
        Events dbEvent = eventsRepository.findByStartAndTitle(event.getStart(), event.getTitle());
        if (dbEvent != null){
            return dbEvent;
        }
        else return event;
    }

    public Set<Events> actualEvents(Set<Events> currentEvents) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Set<Events> actualEvents = new HashSet<>();

        Date currentDate = new Date();
        for(Events event : currentEvents){
            if(simpleDateFormat.parse(event.getStart()).after(currentDate))
                actualEvents.add(event);
        }
        return actualEvents;
    }

    public String toString(Events event){
        User currentUser = findByAuthentication();
        Set<User> participants = event.getEventParticipants();
        Iterator<User> userIterator = participants.iterator();
        List<String> userNames = new ArrayList<>();
        List<String> userPageUrls = new ArrayList<>();
        while(userIterator.hasNext()){
            User user = userIterator.next();
            UserInfo userInfo = userInfoRepository.findByUser(user);
            String userName = "\"" + userInfo.getFirstName() + " " + userInfo.getLastName() + "\"";
            userNames.add(userName);
            if (user.equals(currentUser)){
                String userPageUrl = "\"cabinet\"";
                userPageUrls.add(userPageUrl);
            }
            else {
                String userPageUrl = "\"friend_page/" + user.getUsername() + "\"";
                userPageUrls.add(userPageUrl);
            }

        }
        return "{" +
                "\"id\": " + event.getId() + ", " +
                "\"title\": \"" + event.getTitle() + "\", " +
                "\"start\": \"" + event.getStart() + "\", " +
                "\"allDay\": true, " +
                "\"userNames\": " + userNames.toString() + ", " +
                "\"userPageUrls\": " + userPageUrls.toString() + "}";

    }

}
