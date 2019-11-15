package com.nectcracker.studyproject.service;

import com.nectcracker.studyproject.domain.Chat;
import com.nectcracker.studyproject.domain.News;
import com.nectcracker.studyproject.domain.User;
import com.nectcracker.studyproject.repos.ChatRepository;
import com.nectcracker.studyproject.repos.NewsRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class NewsService {
    private final NewsRepository newsRepository;
    private final ChatRepository chatRepository;

    public NewsService(NewsRepository newsRepository, ChatRepository chatRepository) {
        this.newsRepository = newsRepository;
        this.chatRepository = chatRepository;
    }

    public void createNew(Chat chat, User user){
        News newForFriends = new News();
        newForFriends.setChat(chat);
        Set<User> friends = new HashSet<>(user.getFriends());
        friends.remove(chat.getWishForChat().getUser());
        newForFriends.setUsersNews(friends);
        newsRepository.save(newForFriends);
    }

    public Set<News> findByUser(User user){
        return newsRepository.findAllByUsersNewsContains(user);
    }

}
