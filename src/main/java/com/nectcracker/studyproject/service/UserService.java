package com.nectcracker.studyproject.service;

import com.github.scribejava.apis.VkontakteApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.google.gson.Gson;
import com.nectcracker.studyproject.domain.Role;
import com.nectcracker.studyproject.domain.User;
import com.nectcracker.studyproject.domain.UserInfo;
import com.nectcracker.studyproject.domain.UserRegistrationRequest;
import com.nectcracker.studyproject.json.friendsFromVK.FriendsFromVk;
import com.nectcracker.studyproject.json.friendsFromVK.Nickname;
import com.nectcracker.studyproject.repos.UserInfoRepository;
import com.nectcracker.studyproject.repos.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;

import java.util.regex.Pattern;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserInfoRepository userInfoRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailSender mailSender;

    @Value("${spring.security.oauth2.vk.client.clientId}")
    private String vkClientId;

    @Value("${spring.security.oauth2.vk.client.clientSecret}")
    private String vkClientSecret;

    @Value("${spring.security.oauth2.vk.client.scope}")
    private String vkScope;

    @Value("${spring.security.oauth2.vk.callback}")
    private String vkCallback;

    @Value("${spring.security.oauth2.vk.method}")
    private String vkMethod;

    //Because @Value initialize after OAuth20Service
    private OAuth20Service vkScribejavaService = null;

    private String accessToken;

    private Gson gson = new Gson();

    private Random random = new Random();


    public UserService(UserRepository userRepository, MailSender mailSender, UserInfoRepository userInfoRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.mailSender = mailSender;
        this.userInfoRepository = userInfoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public void setAccessToken(String accessToken){
        this.accessToken = accessToken;
    }

    public String addUser(UserRegistrationRequest userRegistrationRequest) throws IOException, GeneralSecurityException {

        if(!Pattern.matches(".*@.*", userRegistrationRequest.getEmail()))
            return "Wrong e-mail address";

        if (userRepository.findByUsername(userRegistrationRequest.getLogin()) != null || userRepository.findByEmail(userRegistrationRequest.getEmail()) != null)
            return "User exists!";

        User newUser = new User(userRegistrationRequest.getLogin(), passwordEncoder.encode(userRegistrationRequest.getPassword()), userRegistrationRequest.getEmail());
        newUser.setRoles(Collections.singleton(Role.USER));
        newUser.setActivationCode(UUID.randomUUID().toString());

        userRepository.save(newUser);

        if(!StringUtils.isEmpty(newUser.getEmail())) {
            String message = String.format("Hello %s \n" +
                    "Welcome to GiftsWeb, please follow to this link for confirm your account: " +
                    "http://localhost:8080/registration/activate/%s", userRegistrationRequest.getFirst_name(), newUser.getActivationCode());
            mailSender.send(newUser.getEmail(), "Activation code", message);
        }

        UserInfo userInfo = UserInfo.builder()
                .firstName(userRegistrationRequest.getFirst_name())
                .lastName(userRegistrationRequest.getLast_name())
                .birthday(userRegistrationRequest.getBirthday())
                .user(newUser).build();
        userInfoRepository.save(userInfo);
        return "";
    }

    public void addUserFromVk(Map<String, Object> userInfoMap) {
        Long vkId = Long.valueOf((Integer) userInfoMap.get("id"));
        User user = userRepository.findByVkId(vkId);
        if (user == null) {
            user = new User(String.valueOf(vkId), passwordEncoder.encode(String.valueOf(random.nextInt(2147483600))), vkId);
            user.setEmail("q");
            user.setRoles(Collections.singleton(Role.USER));
            user.setConfirmed(true);
            userRepository.save(user);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d.M.yyyy");
            Date bDate = null;
            try {
                if(userInfoMap.get("bdate") != null)
                    bDate = simpleDateFormat.parse((String) userInfoMap.get("bdate"));

            } catch (ParseException e) {
                e.printStackTrace();
            }
            UserInfo userInfo = UserInfo.builder()
                    .firstName((String) userInfoMap.get("first_name"))
                    .lastName((String) userInfoMap.get("last_name"))
                    .birthday(bDate)
                    .user(user).build();

            userInfoRepository.save(userInfo);

        }
    }

    public boolean activateUser(String code) {
        User user = userRepository.findByActivationCode(code);
        if( user == null)
            return false;

        user.setActivationCode(null);
        user.setConfirmed(true);

        userRepository.save(user);
        return true;

    }

    public Map<String, Set> takeFriendFromVk(User user) throws InterruptedException, ExecutionException, IOException {
        Set<User> friendsSetRegistered = new HashSet<>();
        Set<Nickname> friendsNicknamesSetNotRegistered = new HashSet<>();
        if(user.getVkId() != null) {
        vkScribejavaService = new ServiceBuilder(vkClientId)
                .apiSecret(vkClientSecret)
                .defaultScope(vkScope)
                .callback(vkCallback)
                .build(VkontakteApi.instance());

            final OAuthRequest friendsRequest = new OAuthRequest(Verb.GET, "https://api.vk.com/method/friends.get?user_id=" + user.getVkId() + "&fields=nickname&v=" + VkontakteApi.VERSION);
            vkScribejavaService.signRequest(accessToken, friendsRequest);
            final Response friendsResponse = vkScribejavaService.execute(friendsRequest);

            String UserFriendsFromVk = friendsResponse.getBody();
            Set<Nickname> friendsNicknames = gson.fromJson(UserFriendsFromVk, FriendsFromVk.class).response.getItems();


            User friend;
            for (Nickname friendsNickname : friendsNicknames) {
                friend = userRepository.findByVkId(friendsNickname.getId());
                if (friend != null)
                    friendsSetRegistered.add(friend);
                else
                    friendsNicknamesSetNotRegistered.add(friendsNickname);
            }
        }
        if(!user.getFriends().equals(friendsSetRegistered)) {
            user.setFriends(friendsSetRegistered);
            user.setFriendsOf(friendsSetRegistered);
            userRepository.save(user);
        }

        HashMap<String, Set> friendMapForForm= new HashMap<>();
        friendMapForForm.put("registered", friendsSetRegistered);
        friendMapForForm.put("notRegistered", friendsNicknamesSetNotRegistered);
        return friendMapForForm;
    }



}
