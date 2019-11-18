package com.nectcracker.studyproject.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nectcracker.studyproject.domain.Chat;
import com.nectcracker.studyproject.domain.Messages;
import com.nectcracker.studyproject.domain.User;
import com.nectcracker.studyproject.domain.UserWishes;
import com.nectcracker.studyproject.service.ChatService;
import com.nectcracker.studyproject.service.MessagesService;
import com.nectcracker.studyproject.service.UserWishesService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class ChatController {
    private final UserWishesService userWishesService;
    private final ChatService chatService;
    private final MessagesService messagesService;

    public ChatController(UserWishesService userWishesService,
                          ChatService chatService, MessagesService messagesService) {
        this.userWishesService = userWishesService;
        this.chatService = chatService;
        this.messagesService = messagesService;
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
        model.put("userId", currentUser.getId());
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

    @PostMapping("/sendMessage/{wishId}")
    public ModelAndView sendMessageToDB(@PathVariable Long wishId, @RequestParam String sentMessage) {
        messagesService.receiveMessage(sentMessage, wishId);
        ModelAndView modelAndView = new ModelAndView("redirect:/chat");
        modelAndView.addObject("wishId", wishId);
        return modelAndView;
    }

    @PostMapping(value = "/showMessages", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, String> showMessages(@RequestParam Long chatId, @RequestParam Long userId) {
        return messagesService.findMessagesForChat(chatId);
    }
}
