package com.nectcracker.studyproject.controller;

import com.nectcracker.studyproject.domain.UserRegistrationRequest;
import com.nectcracker.studyproject.service.UserService;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@RequestMapping("registration")
@Controller
public class RegistrationController {
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @InitBinder("userRegistrationRequest") // For take Data from login.html
    public void initDateBinder(final WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-mm-dd"), true));
    }

    @GetMapping("")
    public String registration(Model model) {
        model.addAttribute("userRegistrationRequest", new UserController());
        return "registration";
    }

    @PostMapping("")
    public String addUser(@ModelAttribute("userRegistrationRequest") UserRegistrationRequest userRegistrationRequest,
            Map<String, Object> model) {

        if (!userService.addUser(userRegistrationRequest)) {
            model.put("message", "User exists!");
            return "registration";
        }
        return "redirect:/login";
    }

}
