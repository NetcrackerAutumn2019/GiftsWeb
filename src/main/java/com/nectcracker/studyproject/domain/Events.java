package com.nectcracker.studyproject.domain;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;


@Data
@Entity(name = "Events")
public class Events  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToMany(mappedBy = "eventsSet")
    private Set<User> eventParticipants = new HashSet<>();

    @OneToOne(mappedBy = "eventForWish")
    private UserWishes wishForEvent;

    private String title;

    private String start;
    @Column(name = "allDay")
    private boolean allDay;

    public Events(){}

    public Events(String title, String start, Boolean allDay) {
        this.title = title;
        this.start = start;
        this.allDay = allDay;
    }

    @Override
    public String toString(){
        Iterator usersIterator = eventParticipants.iterator();
        List<String> userNames = new ArrayList<>();
        while(usersIterator.hasNext()){
            User user = (User) usersIterator.next();
            String userName = user.getUsername();
            userNames.add(userName);
        }
        return "{" +
                "\"id\": " + id + ", " +
                "\"title\": \"" + title + "\", " +
                "\"start\": \"" + start + "\", " +
                "\"allDay\": true, " +
                "\"description\": " + userNames.toString() + "}";
    }


}
