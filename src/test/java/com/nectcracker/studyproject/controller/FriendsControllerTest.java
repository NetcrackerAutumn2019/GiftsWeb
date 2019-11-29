package com.nectcracker.studyproject.controller;

import com.nectcracker.studyproject.controller.FriendsController;
import com.nectcracker.studyproject.domain.User;
import com.nectcracker.studyproject.repos.UserRepository;
import com.nectcracker.studyproject.service.UserService;
import com.nectcracker.studyproject.service.UserWishesService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-for-interests-test.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD )
@Sql(value = {"/delete-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@WithUserDetails("a")
public class FriendsControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserWishesService userWishesService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @Test
    @Ignore
    public void correctLastName() throws Exception {
        User user = userWishesService.findByAuthentication();
        User friend = userRepository.findAllByFriends(user).iterator().next();
        this.mockMvc.perform(get("/friend_page/" + friend.getUsername()))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("/html/body/div[3]/div[1]/span[2]").string(friend.getInfo().getLastName()));
    }

    @Test
    public void correctWishOnFriendPage() throws Exception{
        User user = userRepository.findByUsername("a");
        userWishesService.addWishfromFriend(user, "wishFromFriend", "");
        userWishesService.addWish("myOwnWish", "");

        this.mockMvc.perform(get("/friend_page/a"))
                .andDo(print())
                .andExpect(content().string(containsString("myOwnWish")))
                .andExpect(content().string(containsString("wishFromFriend")))
                .andExpect(status().isOk());
    }

}