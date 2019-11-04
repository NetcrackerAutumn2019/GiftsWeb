package com.nectcracker.studyproject;

import com.nectcracker.studyproject.service.CalendarService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Date;

@SpringBootApplication
public class Application {
    public static void main(String[] args) throws IOException, GeneralSecurityException {

        SpringApplication.run(Application.class, args);
//        String calendarId = CalendarService.createCalendar();
//        Date date = new Date();
//        CalendarService.createEvent(calendarId, date, "день рождения");
    }
}
