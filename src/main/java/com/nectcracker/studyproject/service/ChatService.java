package com.nectcracker.studyproject.service;

import com.nectcracker.studyproject.domain.Chat;
import com.nectcracker.studyproject.domain.UserWishes;
import com.nectcracker.studyproject.repos.ChatRepository;
import com.nectcracker.studyproject.repos.UserWishesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class ChatService {
    @Autowired
    private UserWishesService userWishesService;
    @Autowired
    private UserWishesRepository userWishesRepository;

    @Autowired
    private ChatRepository chatRepository;

    public void createNewChat(Long id, String description, Date deadline, String sum) {
        UserWishes wishForChat = userWishesService.getById(id);
        log.error("upal");
        Chat tmp = new Chat(description, deadline, Double.parseDouble(sum));
        log.error("upal 1");

        chatRepository.save(tmp);
        log.error("upal 2");

        wishForChat.setChatForWish(tmp);
        wishForChat.setWishHasChat("yes");
        log.error("upal 3");

        userWishesRepository.save(wishForChat);
        log.error("upal 4");

    }
}
