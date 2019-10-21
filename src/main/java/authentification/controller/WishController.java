package authentification.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WishController {
    @GetMapping("/new_wish")
    public String new_wish() {
        return "new_wish";
    }
}
