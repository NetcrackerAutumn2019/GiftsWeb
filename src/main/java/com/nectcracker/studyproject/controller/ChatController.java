package com.nectcracker.studyproject.controller;

import com.nectcracker.studyproject.domain.Chat;
import com.nectcracker.studyproject.domain.User;
import com.nectcracker.studyproject.domain.UserWishes;
import com.nectcracker.studyproject.repos.UserRepository;
import com.nectcracker.studyproject.repos.UserWishesRepository;
import com.nectcracker.studyproject.service.ChatService;
import com.nectcracker.studyproject.service.UserWishesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Slf4j
@Controller
public class ChatController {
    private final UserWishesService userWishesService;
    private final UserWishesRepository userWishesRepository;
    private final ChatService chatService;
    private final UserRepository userRepository;

    public ChatController(UserWishesService userWishesService,
                          UserWishesRepository userWishesRepository,
                          ChatService chatService,
                          UserRepository userRepository) {
        this.userWishesService = userWishesService;
        this.userWishesRepository = userWishesRepository;
        this.chatService = chatService;
        this.userRepository = userRepository;
    }

    @PostMapping("/join_chat/{id}")
    public ModelAndView chatStrategy(@PathVariable Long id) {
        UserWishes wishForChat = userWishesService.getById(id);
        if (chatService.findByWishForChat(wishForChat).isPresent()) {
            ModelAndView modelAndView = new ModelAndView("redirect:/chat");
            modelAndView.addObject("wishId", id);
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("redirect:/new_chat");
            modelAndView.addObject("wishId", id);
            return modelAndView;
        }
    }

    @GetMapping("/chat")
    public String showChat(@RequestParam Long wishId, Map<String, Object> model) {
        UserWishes currentWish = userWishesService.getById(wishId);
        User currentUser = currentWish.getUser();
        model.put("username", currentUser.getInfo().getFirstName());
        model.put("wishInfo", currentWish.getWishName());
        model.put("wishId", wishId);
        chatService.checkUser(currentWish);
        Iterable<User> participants = chatService.getChatParticipants(wishId);
        model.put("participants", participants);
        Chat chat = chatService.getById(wishId);
        model.put("currentPrice", chat.getCurrentPrice());
        model.put("price", chat.getPresentPrice());
        return "chat";
    }

    @GetMapping("/new_chat")
    public ModelAndView showNewChat(@RequestParam Long wishId, Map<String, Object> model) {
        UserWishes currentWish = userWishesService.getById(wishId);
        User currentUser = currentWish.getUser();
        model.put("username", currentUser.getInfo().getFirstName());
        model.put("wishInfo", currentWish.getWishName());
        ModelAndView modelAndView = new ModelAndView("new_chat");
        modelAndView.addObject("wishId", wishId);
        return modelAndView;
    }

    @PostMapping("/new_chat/{id}")
    public ModelAndView createNewChat(@PathVariable Long id, @RequestParam String description,
                                      @RequestParam String deadline, @RequestParam String sum) {
        ModelAndView modelAndView;
        if (!chatService.createNewChat(id, description, deadline, sum)) {
            modelAndView = new ModelAndView("redirect:/new_chat");
            modelAndView.addObject("errorInfo", "Please fill again");

        } else {
            modelAndView = new ModelAndView("redirect:/chat");
        }
        modelAndView.addObject("wishId", id);
        return modelAndView;
    }

    @PostMapping("/donate/{id}")
    public ModelAndView donateMoneyForWish(@PathVariable Long id, @RequestParam String sum) {
        chatService.donateMoneyForWish(id, sum);
        ModelAndView modelAndView = new ModelAndView("redirect:/chat");
        modelAndView.addObject("wishId", id);
        return modelAndView;
    }

    @GetMapping("/chat_list")
    public String showAllChats(Map<String, Object> model) {
        User currentUser = userWishesService.findByAuthentication();
        model.put("chats", currentUser.getUserChats());
        return "chat_list";
    }
}
