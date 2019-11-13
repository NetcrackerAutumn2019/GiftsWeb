package com.nectcracker.studyproject.service;

import com.nectcracker.studyproject.domain.Chat;
import com.nectcracker.studyproject.domain.User;
import com.nectcracker.studyproject.domain.UserWishes;
import com.nectcracker.studyproject.repos.ChatRepository;
import com.nectcracker.studyproject.repos.UserRepository;
import com.nectcracker.studyproject.repos.UserWishesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class ChatService {
    private final UserWishesService userWishesService;
    private final UserWishesRepository userWishesRepository;
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    public ChatService(UserWishesService userWishesService,
                       UserWishesRepository userWishesRepository,
                       ChatRepository chatRepository,
                       UserRepository userRepository) {
        this.userWishesService = userWishesService;
        this.userWishesRepository = userWishesRepository;
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
    }

    public boolean createNewChat(Long id, String description, String deadline, String sum) {
        try {
            UserWishes wishForChat = userWishesService.getById(id);
            if (!deadline.equals("") && !sum.equals("") && sum.matches("([\\d]*[.]?[\\d]+)")) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                Chat tmp = new Chat(description, formatter.parse(deadline), Double.parseDouble(sum));
                tmp.setWishForChat(wishForChat);
                User user = userWishesService.findByAuthentication();
                if (chatRepository.findByWishForChat(wishForChat) == null) {
                    tmp.setOwner(user);
                }
                tmp.getParticipants().add(user);
                chatRepository.save(tmp);
                wishForChat.setChatForWish(tmp);
                userWishesRepository.save(wishForChat);
                return true;
            } else return false;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
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

    public Chat findByOwner(User user){
        return chatRepository.findByOwner(user);
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
