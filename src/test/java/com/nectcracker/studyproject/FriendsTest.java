package com.nectcracker.studyproject;

import com.nectcracker.studyproject.controller.FriendsController;
import com.nectcracker.studyproject.domain.User;
import com.nectcracker.studyproject.service.UserService;
import com.nectcracker.studyproject.service.UserWishesService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("179197505")
public class FriendsTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserWishesService userWishesService;

    @Autowired
    private UserService userService;


    @Test
    public void correctLastName() throws Exception {
        this.mockMvc.perform(get("/friend_page/133344005"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("/html/body/div[3]/div[1]/span[2]").string("Бурцева"));
    }

    @Test
    public void correctWishOnFriendPage() throws Exception{
        userWishesService.addWishfromFriend((User) userService.loadUserByUsername("179197505"), "wishFromFriend");
        userWishesService.addWish("myOwnWish");
        this.mockMvc.perform(get("/friend_page/179197505"))
                .andDo(print())
                .andExpect(xpath("/html/body/div[3]/div[7]/div[*]/div/div/div/h5/span").string("myOwnWish"))
                .andExpect(xpath("/html/body/div[3]/div[8]/div[*]/div/div/div/h5/span").string("wishFromFriend"));
    }

}