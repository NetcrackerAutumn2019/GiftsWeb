package com.nectcracker.studyproject.controller;

import com.nectcracker.studyproject.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.Map;

@Slf4j
@Controller
public class UserProfileController {
    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("/redactor")
    public String redactor() {
        return "redactor";
    }

    @PostMapping("/redactor")
    public String updateInfo(@RequestParam String name, @RequestParam String surname, @RequestParam String oldPassword,
                             @RequestParam String newPassword,
                             Map<String, Object> model) {
        if (userInfoService.updateUserInfo(name, surname, oldPassword, newPassword)) {
            model.put("result", "Success!");
        } else {
            model.put("result", "Something wrong");
        }
        return "redactor";
    }
}
