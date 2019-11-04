package com.nectcracker.studyproject.service;

import com.nectcracker.studyproject.domain.User;
import com.nectcracker.studyproject.domain.UserInfo;
import com.nectcracker.studyproject.repos.UserInfoRepository;
import com.nectcracker.studyproject.repos.UserRepository;
import com.nectcracker.studyproject.repos.UserRepositoryCustom;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserInfoService implements UserRepositoryCustom {
    private final UserInfoRepository userInfoRepository;
    private final UserRepository userRepository;

    public UserInfoService(UserInfoRepository userInfoRepository, UserRepository userRepository) {
        this.userInfoRepository = userInfoRepository;
        this.userRepository = userRepository;
    }

    @Override
    public User findByAuthentication() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUsername(auth.getName());
    }

    public boolean addUserInfo(UserInfo userInfo) {
        try {
            userInfoRepository.save(userInfo);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean updateUserInfo(String name, String surname, String oldPassword, String newPassword) {
        try {
            User currentUser = findByAuthentication();
            UserInfo currentUserInfo = userInfoRepository.findByUser(currentUser);
            if (!name.equals("")) {
                currentUserInfo.setFirstName(name);
            }
            if (!surname.equals("")) {
                currentUserInfo.setLastName(surname);
            }
            if (!oldPassword.equals("") && !newPassword.equals("") && oldPassword.equals(currentUser.getPassword())) {
                currentUser.setPassword(newPassword);
            }
            userInfoRepository.save(currentUserInfo);
            userRepository.save(currentUser);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

}
