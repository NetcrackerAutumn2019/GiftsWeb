package com.nectcracker.studyproject.controller;

import com.nectcracker.studyproject.domain.User;
import com.nectcracker.studyproject.domain.UserInfo;
import com.nectcracker.studyproject.service.UserService;
import com.nectcracker.studyproject.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserInfoService userInfoService;

    @GetMapping
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(
            @RequestParam String login, @RequestParam String password,
            @RequestParam String first_name, @RequestParam String last_name,
            @RequestParam String email,
            @RequestParam("birthday") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date birthday,
            Map<String, Object> model) {

        User newUser = new User(login, password);
        UserInfo uI = new UserInfo(first_name, last_name, email, birthday, new User(login, password));

        if (!userService.addUser(new User(login, password)) || !userInfoService.addUserInfo(uI)) {
            model.put("message", "User exists!");
            return "registration";
        }

        return "redirect:/login";
    }

}
