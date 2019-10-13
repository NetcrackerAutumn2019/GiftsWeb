package example;

import example.domain.User;
import example.repos.Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class GetController {

    @Autowired
    private Repo repo;

    @GetMapping("/get/{id}")
    public String get(@PathVariable String id, Map<String, Object> model) {
        Iterable<User> messages = repo.findAll();
        User tmp = new User("Not Found");
        for (User t : messages) {
            if(t.getId() == Integer.parseInt(id)) {
                tmp.setId(t.getId());
                tmp.setText(t.getText());
                break;
            }

        }
        System.out.println(tmp.getText());
        if (tmp.getId() != null)
            model.put("textP", tmp.getText() );
        else model.put("textP", "Not found" );
        return "get";
    }

}
