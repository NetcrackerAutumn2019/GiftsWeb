package com.nectcracker.studyproject.service;

import com.nectcracker.studyproject.domain.Role;
import com.nectcracker.studyproject.domain.User;
import com.nectcracker.studyproject.domain.UserInfo;
import com.nectcracker.studyproject.domain.UserRegistrationRequest;
import com.nectcracker.studyproject.repos.UserInfoRepository;
import com.nectcracker.studyproject.repos.UserRepository;
import com.vk.api.sdk.streaming.queries.rules.StreamingGetRulesQuery;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.Collections;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserInfoRepository userInfoRepository;
    private final MailSender mailSender;

    public UserService(UserRepository userRepository, UserInfoRepository userInfoRepository, MailSender mailSender) {
        this.userRepository = userRepository;
        this.userInfoRepository = userInfoRepository;
        this.mailSender = mailSender;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public String addUser(UserRegistrationRequest userRegistrationRequest) {
        if(!Pattern.matches(".*@.*", userRegistrationRequest.getEmail()))
            return "Wrong e-mail address";

        if (userRepository.findByUsername(userRegistrationRequest.getLogin()) != null || userRepository.findByEmail(userRegistrationRequest.getEmail()) != null)
            return "User exists!";

        User user = new User(userRegistrationRequest.getLogin(), userRegistrationRequest.getPassword(), userRegistrationRequest.getEmail());
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());
        userRepository.save(user);

        if(!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format("Hello %s \n" +
                    "Welcome to GiftsWeb, please follow to this link for confirm your account: " +
                    "http://localhost:8080/registration/activate/%s", userRegistrationRequest.getFirst_name(), user.getActivationCode());
            mailSender.send(user.getEmail(), "Activation code", message);
        }

        userInfoRepository.save(UserInfo.builder()
                .firstName(userRegistrationRequest.getFirst_name())
                .lastName(userRegistrationRequest.getLast_name())
                .birthday(userRegistrationRequest.getBirthday())
                .user(user).build());
        return "";
    }

    public void addUserFromVK(User user){
        userRepository.save(user);
        String message = String.format("Hello \n" +
                "what is your template login and password \n" +
                "login: %s \n"+
                "password: %s",user.getUsername(), user.getPassword());

        mailSender.send(user.getEmail(), "Login and password", message);
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
}
