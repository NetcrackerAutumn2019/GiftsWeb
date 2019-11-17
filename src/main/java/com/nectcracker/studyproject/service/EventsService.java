package com.nectcracker.studyproject.service;

import com.nectcracker.studyproject.domain.Events;
import com.nectcracker.studyproject.domain.User;
import com.nectcracker.studyproject.repos.EventsRepository;
import com.nectcracker.studyproject.repos.UserRepository;
import com.nectcracker.studyproject.repos.UserRepositoryCustom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;
@Slf4j
@Service
public class EventsService implements UserRepositoryCustom {
    private EventsRepository eventsRepository;
    private UserRepository userRepository;
    private UserService userService;

    @Override
    public User findByAuthentication() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) userService.loadUserByUsername(auth.getName());
        return user;
    }

    public Set<Events> getUserEvents() {
        User currentUser = findByAuthentication();
        return currentUser.getEventsSet();
    }

    public void createEvent (String title, String start, User user, Boolean allDay){
        Events event = new Events(title, start, allDay);
        event.getEventParticipants().add(user);
        eventsRepository.save(event);
    }

    public void updateEvent(Events event, List<User> participants){
        event.getEventParticipants().addAll(participants);
        eventsRepository.save(event);
    }
}
