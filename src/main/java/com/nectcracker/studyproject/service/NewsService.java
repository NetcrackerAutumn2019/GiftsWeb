package com.nectcracker.studyproject.service;

import com.nectcracker.studyproject.domain.Chat;
import com.nectcracker.studyproject.domain.News;
import com.nectcracker.studyproject.domain.NewsUsers;
import com.nectcracker.studyproject.domain.User;
import com.nectcracker.studyproject.repos.NewsRepository;
import com.nectcracker.studyproject.repos.NewsUsersRepository;
import com.nectcracker.studyproject.repos.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class NewsService {
    private final NewsRepository newsRepository;
    private final NewsUsersRepository newsUsersRepository;

    public NewsService(NewsRepository newsRepository, NewsUsersRepository newsUsersRepository) {
        this.newsRepository = newsRepository;
        this.newsUsersRepository = newsUsersRepository;
    }

    private void createNew(Chat chat, User user, String text) {
        News newForFriends = new News();
 //       newForFriends.setChat(chat);
        newForFriends.setText(text);
        Set<User> friends = new HashSet<>(user.getFriends());
        User wishOwnerUser = chat.getWishForChat().getUser();
        friends.retainAll(wishOwnerUser.getFriends());
        if (!friends.isEmpty()) {
            newForFriends.addAllUsers(friends);

            newsRepository.save(newForFriends);
            newsUsersRepository.saveAll(newForFriends.getUsers());
        }
    }

    public void creatNewChatCreated(Chat chat, User user) {
        String text = "Ваш друг " + user.getInfo().getFirstName() + " " + user.getInfo().getLastName() +
                " начал сбор средств на " + chat.getWishForChat().getWishName() + " для другого вашего друга " + chat.getWishForChat().getUser().getInfo().getFirstName() +
                " " + chat.getWishForChat().getUser().getInfo().getLastName();
        createNew(chat, user, text);
    }

    public void createNewMoneyCollected(Chat chat, User user) {
        String text = "Средства на " + chat.getWishForChat().getWishName() + " для другого вашего друга " + chat.getWishForChat().getUser().getInfo().getFirstName() +
                " " + chat.getWishForChat().getUser().getInfo().getLastName() + " были успешно собраны";
        createNew(chat, user, text);
    }

    public void createNewChatIsClosed(Chat chat, User user) {
        String text = "Пришел дедлайн и " +  user.getInfo().getFirstName() + " " + user.getInfo().getLastName() + " решил, не продолжать сбор средств на" +
                chat.getWishForChat().getWishName() + " для другого вашего друга " + chat.getWishForChat().getUser().getInfo().getFirstName() +
                " " + chat.getWishForChat().getUser().getInfo().getLastName();
        createNew(chat, user, text);
    }


    public Map<String, Set<News>> findByUser(User user) {
        Map<String, Set<News>> resultMap = new HashMap<>();
        Set<News> newNews = new HashSet<>();
        Set<News> oldNews = new HashSet<>();

        Set<NewsUsers> nu = newsUsersRepository.findAllByUsers(user);

        for (NewsUsers iterator : nu) {
            if (iterator.isSaw()) {
                oldNews.add(iterator.getNews());
            } else {
                newNews.add(iterator.getNews());
                iterator.setSaw(true);
                newsUsersRepository.save(iterator);
            }
        }

        resultMap.put("oldNews", oldNews);
        resultMap.put("newNews", newNews);

        return resultMap;
    }

    public int sizeOfNewsByUser(User user) {
        return newsUsersRepository.countAllByUsers(user);

    }

}
