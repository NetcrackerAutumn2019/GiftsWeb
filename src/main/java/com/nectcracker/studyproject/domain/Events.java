package com.nectcracker.studyproject.domain;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;



@Data
@Entity(name = "Events")
public class Events  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(mappedBy = "eventsSet")
    private Set<User> eventParticipants;

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

//    @Override
//    public String toString(){
//        return "{" +
//                "\"id\": " + id + ", " +
//                "\"title\": \'" + title + "\', " +
//                "\"start\": \'" + start + "\', " +
//                "\"allDay\": true, " +
//                "\"description\": \'" + getEventParticipants() +
//                "\'}";
//    }


}
