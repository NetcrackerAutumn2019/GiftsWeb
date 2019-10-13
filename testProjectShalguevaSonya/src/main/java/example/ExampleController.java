package example;
import example.domain.User;
import example.repos.Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class ExampleController {
    @Autowired
    private Repo repo;

    @GetMapping("/send")
    public String main(Map<String, Object> model) {
        Iterable<User> messages = repo.findAll();
        model.put("messages",messages );
        model.put("textP", ":(");
        return "send";
    }

    @RequestMapping("/send/{text}")
    public String add(@PathVariable String text, Map<String, Object> model) {
        User m = new User(text);
        repo.save(m);
        Iterable<User> messages = repo.findAll();
        model.put("messages", messages );
        return "send";
    }

}
