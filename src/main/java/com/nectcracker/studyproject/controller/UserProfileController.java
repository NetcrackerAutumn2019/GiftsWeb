package com.nectcracker.studyproject.controller;

import com.nectcracker.studyproject.domain.User;
import com.nectcracker.studyproject.domain.UserInfo;
import com.nectcracker.studyproject.repos.UserInfoRepository;
import com.nectcracker.studyproject.repos.UserRepository;
import com.nectcracker.studyproject.service.UserInfoService;
import com.nectcracker.studyproject.service.UserService;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Slf4j
@Controller
public class UserProfileController {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserService userService;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/redactor")
    public String redactor() {
        return "redactor";
    }

    @PostMapping("/redactor")
    public String updateInfo(@RequestParam String name, @RequestParam String surname, @RequestParam String oldPassword,
                             @RequestParam String newPassword,
                             Map<String, Object> model) {
//        if (userInfoService.updateUserInfo(name, surname, oldPassword, newPassword)) {
//            model.put("result", "Success!");
//        } else {
//            model.put("result", "Something wrong");
//        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.findByUsername(auth.getName());
        UserInfo currentUserInfo = userInfoRepository.findByUser(currentUser);
//        currentUserInfo.setFirstName(name);
//        currentUserInfo.setLastName(surname);
//        currentUser.setPassword(newPassword);
//        userInfoRepository.save(currentUserInfo);
//        userRepository.save(currentUser);

        if (!name.equals("")) {
            currentUserInfo.setFirstName(name);
        }
        if(!surname.equals("")) {
            currentUserInfo.setLastName(surname);
        }
        if (!oldPassword.equals("") && !newPassword.equals("") && oldPassword.equals(currentUser.getPassword())) {
            currentUser.setPassword(newPassword);
        }
        userInfoRepository.save(currentUserInfo);
        userRepository.save(currentUser);
        return "redactor";
    }
}
