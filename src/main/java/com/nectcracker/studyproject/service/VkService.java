package com.nectcracker.studyproject.service;

import com.github.scribejava.apis.VkontakteApi;
import com.github.scribejava.apis.vk.VKOAuth2AccessToken;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;

import com.google.gson.Gson;
import com.nectcracker.studyproject.domain.Role;
import com.nectcracker.studyproject.domain.User;
import com.nectcracker.studyproject.domain.UserInfo;
import com.nectcracker.studyproject.json.friendsFromVK.FriendsFromVk;
import com.nectcracker.studyproject.json.userFromVK.UserFromVk;
import com.nectcracker.studyproject.json.userFromVK.UserInfoFromVk;
import com.nectcracker.studyproject.repos.UserInfoRepository;
import com.nectcracker.studyproject.repos.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;


@Service
public class VkService {
    private final UserRepository userRepository;
    private final UserInfoRepository userInfoRepository;
    private final MailSender mailSender;

    public VkService(UserRepository userRepository, UserInfoRepository userInfoRepository, MailSender mailSender) {
        this.userRepository = userRepository;
        this.userInfoRepository = userInfoRepository;
        this.mailSender = mailSender;
    }

    @Value("${spring.oauth2.vk.method}")
    private String vkMethod;

    private Gson gson = new Gson();

    private Random random = new Random();

    public String addUser(OAuth2AccessToken accessToken, Response response) throws IOException, ParseException {

        String infoAboutUserFromVk = response.getBody();
        UserInfoFromVk userInfoFromVk = gson.fromJson(infoAboutUserFromVk, UserFromVk.class).response.get(0);
        if(userRepository.findByVkId(userInfoFromVk.getId()) != null){
           return String.valueOf(userInfoFromVk.getId());
        }

        User newUser = new User();
        if(accessToken instanceof VKOAuth2AccessToken)
            newUser.setEmail(((VKOAuth2AccessToken) accessToken).getEmail());
        newUser.setVkId(userInfoFromVk.getId());
        newUser.setRoles(Collections.singleton(Role.USER));
        newUser.setUsername(String.valueOf(userInfoFromVk.getId()));
        newUser.setPassword(String.valueOf(random.nextInt(2147483600)));
        userRepository.save(newUser);

        String message = String.format("Hello \n" +
                "what is your template login and password \n" +
                "login: %s \n"+
                "password: %s",newUser.getUsername(), newUser.getPassword());
        mailSender.send(newUser.getEmail(), "Login and password", message);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d.M.yyyy");
        UserInfo newUserInfo = UserInfo.builder()
                .firstName(userInfoFromVk.getFirst_name())
                .lastName(userInfoFromVk.getLast_name())
                .birthday(simpleDateFormat.parse(userInfoFromVk.getBirthday()))
                .user(newUser).build();

        userInfoRepository.save(newUserInfo);
        return "";
    }

    //public void authentication(HttpServletRequest httpServletRequest, Response response) throws IOException {
//        String infoAboutUserFromVk = response.getBody();
//        UserInfoFromVk userInfoFromVk = gson.fromJson(infoAboutUserFromVk, UserFromVK.class).response.get(0);
//        String usernameAndPassword = userRepository.findByVkId(userInfoFromVk.getId()).getUsername();
//
//        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usernameAndPassword, usernameAndPassword));
//        SecurityContext securityContext = SecurityContextHolder.getContext();
//        securityContext.setAuthentication(authentication);
//
//        HttpSession session = httpServletRequest.getSession(true);
//        session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
//    }

    public void addFriends(OAuth20Service vkScribejavaService, OAuth2AccessToken accessToken, String vkId) throws IOException, ExecutionException, InterruptedException {
        final OAuthRequest friendsRequest = new OAuthRequest(Verb.GET, "https://api.vk.com/method/friends.get?user_id=" + vkId + "&v=" + VkontakteApi.VERSION);
        vkScribejavaService.signRequest(accessToken, friendsRequest);
        final Response friendsResponse = vkScribejavaService.execute(friendsRequest);

        User user = userRepository.findByVkId(Long.parseLong(vkId));
        String UserFriendsFromVk = friendsResponse.getBody();
        List<Long> friendsVkId = gson.fromJson(UserFriendsFromVk, FriendsFromVk.class).response.getItems();

        Set<User> friendsSet = new HashSet<>();
        User friend;
        for(Long friendVkId : friendsVkId){
            friend = userRepository.findByVkId(friendVkId);
            if(friend != null) {
                friendsSet.add(friend);
            }
        }

        if(friendsSet.size() != 0) {
            user.setFriends(friendsSet);
            user.setFriendsOf(friendsSet);
            userRepository.save(user);
        }
    }
}


