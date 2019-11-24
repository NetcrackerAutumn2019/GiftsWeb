package com.nectcracker.studyproject;

import com.nectcracker.studyproject.domain.News;
import com.nectcracker.studyproject.domain.User;
import com.nectcracker.studyproject.domain.UserWishes;
import com.nectcracker.studyproject.repos.NewsUsersRepository;
import com.nectcracker.studyproject.repos.UserRepository;
import com.nectcracker.studyproject.service.ChatService;
import com.nectcracker.studyproject.service.NewsService;
import com.nectcracker.studyproject.service.UserWishesService;
import org.checkerframework.checker.units.qual.A;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("179197505")
public class NewsTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    NewsService newsService;

    @Autowired
    ChatService chatService;

    @Autowired
    UserWishesService userWishesService;

    @Autowired
    NewsUsersRepository newsUsersRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    public void auth() throws Exception {
        this.mockMvc.perform(get("/cabinet"))
                .andDo(print())
                .andExpect(authenticated());
    }

//    @Test
//    public void createNew(){
//        User currentUser = userRepository.findByVkId((long) 179197505);
//        Set<User> friends = userRepository.findAllByFriends(currentUser);
//        Set<User> commonFriends = new HashSet<>(friends);
//        commonFriends.retainAll(userRepository.findAllByFriends(friends.iterator().next()));
//        userWishesService.addWishfromFriend(friends.iterator().next(),"something");
//        chatService.createNewChat(userWishesService.getUserWishes(friends.iterator().next()).iterator().next().getId(), "asd", "2019-12-11", "500");
//        //newsService.createNew(chatService.findByOwner(currentUser), currentUser);
//        Map<String, Set<News>> map= newsService.findByUser(commonFriends.iterator().next());
//        News news = map.get("newNews").iterator().next();
//        assertEquals(news.getChat().getOwner(), currentUser);
//    }
//
//    @Test
//    public void findNewsByUser(){
//
//    }
}
