package authentification.controller;

import authentification.domain.User;
import authentification.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    private UserService userService;

    @GetMapping
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@RequestParam String login,  @RequestParam String password, Map<String, Object> model) {

        if (!userService.addUser(new User(login, password))) {
            model.put("message", "User exists!");
            return "registration";
        }

        return "redirect:/login";
    }

}
