package com.nectcracker.studyproject.service;

import com.nectcracker.studyproject.domain.Role;
import com.nectcracker.studyproject.domain.User;
import com.nectcracker.studyproject.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
//    @Autowired
//    private UserInfoRepository userInfoRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public boolean addUser(User user) {
        User userFromDb = userRepository.findByUsername(user.getUsername());

        if (userFromDb != null) {
            return false;
        }

        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);
        return true;
    }
//    public void addUserInfo(UserInfo userInfo){
//        userInfoRepository.save(userInfo);
//    }
}
