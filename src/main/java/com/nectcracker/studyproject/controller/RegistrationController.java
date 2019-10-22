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
        UserInfo uI = new UserInfo(first_name, last_name, email, birthday, newUser);

        if (!userService.addUser(newUser)) {
            model.put("message", "User exists!");
            return "registration";
        }
        userInfoService.addUserInfo(uI);
        return "redirect:/login";
    }

}
