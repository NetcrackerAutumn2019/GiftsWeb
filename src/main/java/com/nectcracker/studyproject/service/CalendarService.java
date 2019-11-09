package com.nectcracker.studyproject.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;
import com.nectcracker.studyproject.domain.User;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;


public class CalendarService {

    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    //private static final String CREDENTIALS_FILE_PATH = "/giftsWebCredentials.json";
    public static String createCalendar() throws IOException, GeneralSecurityException {
        GoogleCredential credential = GoogleCredential.fromStream(new FileInputStream("src\\main\\resources\\giftsWebCredentials.json"))
                .createScoped(Collections.singleton(CalendarScopes.CALENDAR));
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName("GiftsWeb")
                .build();
        com.google.api.services.calendar.model.Calendar calendar = new com.google.api.services.calendar.model.Calendar();
        calendar.setSummary("Ваш календарь");
        calendar.setTimeZone("Europe/Moscow");
        com.google.api.services.calendar.model.Calendar createdCalendar = service.calendars().insert(calendar).execute();
        System.out.println(createdCalendar.getId());
        return createdCalendar.getId();
    }

    public static void createEvent(String calendarId, Date eventStartDate, String eventSummary) throws IOException, GeneralSecurityException {
        GoogleCredential credential = GoogleCredential.fromStream(new FileInputStream("src\\main\\resources\\giftsWebCredentials.json"))
                .createScoped(Collections.singleton(CalendarScopes.CALENDAR));
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName("GiftsWeb")
                .build();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        String eventStart = df.format(eventStartDate);
        DateTime now = new DateTime(System.currentTimeMillis());
        Event event = new Event()
                .setSummary(eventSummary)
                .setLocation("Location")
                .setDescription("Description");
        DateTime startDateTime = new DateTime(eventStart);
        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone("Europe/Moscow");
        event.setStart(start);

//        DateTime endDateTime = new DateTime("2019-11-28T17:00:00-07:00");
//        EventDateTime end = new EventDateTime()
//                .setDateTime(endDateTime)
//                .setTimeZone("Europe/Moscow");
//        event.setEnd(end);
//        event = service.events().insert(calendarId, event).execute();
//        System.out.printf("Event created: %s\n", event.getHtmlLink());
//
//        Events events = service.events().list(calendarId)
//                .setMaxResults(10)
//                .setTimeMin(now)
//                .setOrderBy("startTime")
//                .setSingleEvents(true)
//                .execute();
//        List<Event> items = events.getItems();
//        if (items.isEmpty()) {
//            System.out.println("No upcoming events found.");
//        } else {
//            System.out.println("Upcoming events");
//            for (Event event1 : items) {
//                DateTime start1 = event1.getStart().getDateTime();
//                if (start1 == null) {
//                    start1 = event1.getStart().getDate();
//                }
//                System.out.printf("%s (%s)\n", event1.getSummary(), start1);
//            }
//        }

    }
}
