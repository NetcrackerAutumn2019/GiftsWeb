package com.nectcracker.studyproject.controller;

import com.nectcracker.studyproject.domain.UserRegistrationRequest;
import com.nectcracker.studyproject.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RegistrationControllerTest {

    @Autowired
    private UserService userService;

    @Test
    public void wrongEmail()throws Exception{
        UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest();
        userRegistrationRequest.setLogin("w");
        userRegistrationRequest.setPassword("w");
        userRegistrationRequest.setEmail("agmail.com");
        userRegistrationRequest.setFirst_name("Вася");
        userRegistrationRequest.setLast_name("Пупкин");
        userRegistrationRequest.setBirthday(new Date());
        assertEquals(userService.addUser(userRegistrationRequest), "Wrong e-mail address");
    }

    @Test
    public void userAlreadyExist()throws Exception{
        UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest();
        userRegistrationRequest.setLogin("e");
        userRegistrationRequest.setPassword("e");
        userRegistrationRequest.setEmail("ab@gmail.com");
        userRegistrationRequest.setFirst_name("Вася");
        userRegistrationRequest.setLast_name("Пупкин");
        userRegistrationRequest.setBirthday(new Date());
        userService.addUser(userRegistrationRequest);
        assertEquals(userService.addUser(userRegistrationRequest), "User exists!");
    }
}
