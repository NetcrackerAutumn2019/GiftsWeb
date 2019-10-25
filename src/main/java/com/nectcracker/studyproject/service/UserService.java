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

import java.util.Collections;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserInfoRepository userInfoRepository;

    public UserService(UserRepository userRepository, UserInfoRepository userInfoRepository) {
        this.userRepository = userRepository;
        this.userInfoRepository = userInfoRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public boolean addUser(UserRegistrationRequest userRegistrationRequest) {
        if (userRepository.findByUsername(userRegistrationRequest.getLogin()) != null)
            return false;

        User user = new User(userRegistrationRequest.getLogin(), userRegistrationRequest.getPassword(), userRegistrationRequest.getEmail());
        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);

//        UserInfo userInfo = new UserInfo();
//        userInfo.setUser(user);
//        userInfo.setFirstName(first_name);
//        userInfo.setLastName(last_name);
//        userInfo.setEmail(email);
//        userInfo.setBirthday(birthday);
//        userInfoService.addUserInfo(new UserInfo().toBuilder().user());
        userInfoRepository.save(UserInfo.builder()
                .firstName(userRegistrationRequest.getFirst_name())
                .lastName(userRegistrationRequest.getLast_name())
                .birthday(userRegistrationRequest.getBirthday())
                .user(user).build());
        return true;
    }
}
