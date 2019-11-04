package com.nectcracker.studyproject.service;

import com.nectcracker.studyproject.domain.Role;
import com.nectcracker.studyproject.domain.User;
import com.nectcracker.studyproject.domain.UserInfo;
import com.nectcracker.studyproject.domain.UserRegistrationRequest;
import com.nectcracker.studyproject.repos.UserInfoRepository;
import com.nectcracker.studyproject.repos.UserRepository;
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

    private final MailSender mailSender;

    private final UserInfoRepository userInfoRepository;

    public UserService(UserRepository userRepository, MailSender mailSender, UserInfoRepository userInfoRepository) {
        this.userRepository = userRepository;
        this.mailSender = mailSender;
        this.userInfoRepository = userInfoRepository;
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

        User newUser = new User(userRegistrationRequest.getLogin(), userRegistrationRequest.getPassword(), userRegistrationRequest.getEmail());
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
