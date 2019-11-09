package com.nectcracker.studyproject.service;

import com.nectcracker.studyproject.domain.Chat;
import com.nectcracker.studyproject.domain.User;
import com.nectcracker.studyproject.domain.UserWishes;
import com.nectcracker.studyproject.repos.ChatRepository;
import com.nectcracker.studyproject.repos.UserRepository;
import com.nectcracker.studyproject.repos.UserWishesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class ChatService {
    @Autowired
    private UserWishesService userWishesService;
    @Autowired
    private UserWishesRepository userWishesRepository;
    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private UserRepository userRepository;

    public void createNewChat(Long id, String description, String deadline, String sum) {
        try {
            UserWishes wishForChat = userWishesService.getById(id);
            SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy", Locale.ENGLISH);
            //TODO frankly speaking, have some problems with date - it doesn't parse correctly
            Chat tmp = new Chat(description, formatter.parse(deadline), Double.parseDouble(sum));
            tmp.setWishForChat(wishForChat);
            tmp.setOwner(userWishesService.findByAuthentication());
            User user = userWishesService.findByAuthentication();
            tmp.getParticipants().add(user);
            chatRepository.save(tmp);
            wishForChat.setChatForWish(tmp);
            userWishesRepository.save(wishForChat);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Set<User> getChatParticipants(Long wishId) {
        UserWishes userWishes = userWishesService.getById(wishId);
        Chat currentChat = chatRepository.findByWishForChat(userWishes);
        return currentChat.getParticipants();
    }

    public Optional<Chat> findByWishForChat(UserWishes wishForChat) {
        return chatRepository.findById(wishForChat.getId());
    }

    public void checkUser(UserWishes wish) {
        User currentUser = userWishesService.findByAuthentication();
        Chat currentChat = chatRepository.findByWishForChat(wish);
        currentChat.getParticipants().add(currentUser);
        chatRepository.save(currentChat);
        userRepository.save(currentUser);
    }

    public void donateMoneyForWish(Long wishId, String sum) {
        UserWishes wish = userWishesService.getById(wishId);
        Chat currentChat = chatRepository.findByWishForChat(wish);
        currentChat.updateCurrentPrice(Double.parseDouble(sum));
        chatRepository.save(currentChat);
    }

    public Chat getById(Long wishId) {
        UserWishes wish = userWishesService.getById(wishId);
        return chatRepository.findByWishForChat(wish);
    }
}
