package com.nectcracker.studyproject.controller;
import com.nectcracker.studyproject.domain.UserRegistrationRequest;
import com.nectcracker.studyproject.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@RequestMapping("registration")
@Controller
@Slf4j
public class RegistrationController {
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }


    @InitBinder("userRegistrationRequest") // For take Data from login.html
    public void initDateBinder(final WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-mm-dd"), true));
    }

    @GetMapping()
    public String registration() {
        return "registration";
    }

    @PostMapping()
    public String addUser(@ModelAttribute("userRegistrationRequest") UserRegistrationRequest userRegistrationRequest, Map<String, Object> model) throws IOException, GeneralSecurityException {

        String message = userService.addUser(userRegistrationRequest);

        if (!StringUtils.isEmpty(message)) {
            model.put("message", message);
            return "registration";
        }

        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        boolean isActivated = userService.activateUser(code);

        if (isActivated)
            model.addAttribute("message", "Success");
        else
            model.addAttribute("message", "Sorry, something was wrong");

        return "login";
    }

}

