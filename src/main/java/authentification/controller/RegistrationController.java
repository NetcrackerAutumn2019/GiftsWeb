package authentification.controller;

import authentification.domain.Role;
import authentification.domain.User;
import authentification.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepository userRepository;
    @GetMapping
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@RequestParam String login,  @RequestParam String password, Map<String, Object> model) {
        User tmp = userRepository.findByUsername(login);
        if (tmp != null) {
            model.put("message", "User exists!");
            return "registration";
        }

        User user = new User(login, password);
        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);
        Iterable<User> u = userRepository.findAll();
        for (User y : u) System.out.println(y.getUsername() + " " + y.getPassword());
        return "redirect:/login";
    }

}
