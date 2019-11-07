package com.nectcracker.studyproject.controller;

import com.nectcracker.studyproject.domain.UserWishes;
import com.nectcracker.studyproject.repos.UserWishesRepository;
import com.nectcracker.studyproject.service.ChatService;
import com.nectcracker.studyproject.service.UserWishesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.Map;

@Slf4j
@Controller
public class ChatController {
    @Autowired
    private UserWishesService userWishesService;
    @Autowired
    private UserWishesRepository userWishesRepository;
    @Autowired
    private ChatService chatService;

    @PostMapping("/join_chat/{id}")
    public String chatStrategy(@PathVariable Long id) {
        UserWishes wishForChat = userWishesService.getById(id);
        if (wishForChat.getWishHasChat().equals("yes")) {
            return "redirect:/chat/{id}";
        } else {
            return "redirect:/new_chat?id={id}";
        }
    }

    @GetMapping("/chat/{id}")
    public String showChat() {
        return "chat/{id}";
    }

    @GetMapping("/new_chat")
    public String showNewChat(Map<String, Object> model) {
        return "new_chat";
    }

    @PostMapping("/new_chat")
    public String createNewChat(@PathVariable Long id, @RequestParam String description,
                                @RequestParam Date deadline, @RequestParam String sum) {
        log.error("upal az tut");

        chatService.createNewChat(id, description, deadline, sum);
        log.error("upal ttttut");

        return "redirect:/friend_page";
    }
}
