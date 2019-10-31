package com.nectcracker.studyproject.service;

import com.nectcracker.studyproject.domain.User;
import com.nectcracker.studyproject.domain.UserInfo;
import com.nectcracker.studyproject.repos.UserInfoRepository;
<<<<<<< HEAD
import org.springframework.stereotype.Service;

@Service
public class UserInfoService {
    private final UserInfoRepository userInfoRepository;

    public UserInfoService(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }


    public void addUserInfo(UserInfo userInfo){
        userInfoRepository.save(userInfo);
=======
import com.nectcracker.studyproject.repos.UserRepository;
import com.nectcracker.studyproject.repos.UserRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserInfoService implements UserRepositoryCustom {
    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByAuthentication() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUsername(auth.getName());
    }

    public boolean addUserInfo(UserInfo userInfo) {
        try {
            userInfo.setUser(userInfo.getUser());
            userInfo.setFirstName(userInfo.getFirstName());
            userInfo.setLastName(userInfo.getLastName());
            userInfo.setEmail(userInfo.getEmail());
            userInfo.setBirthday(userInfo.getBirthday());
            userInfoRepository.save(userInfo);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean updateUserInfo(String name, String surname, String oldPassword, String newPassword) {
<<<<<<< HEAD
        User currentUser = findByAuthentication();
        UserInfo currentUserInfo = userInfoRepository.findByUser(currentUser);
//        if (!name.equals("")) {
//            currentUserInfo.setFirstName(name);
//        }
//        if(!surname.equals("")) {
//            currentUserInfo.setLastName(surname);
//        }
//        if (!oldPassword.equals("") && !newPassword.equals("") && oldPassword.equals(currentUser.getPassword())) {
//            currentUser.setPassword(newPassword);
//        }
        currentUserInfo.setFirstName(name);
        currentUserInfo.setLastName(surname);
        currentUser.setPassword(newPassword);
        userInfoRepository.save(currentUserInfo);
        userRepository.save(currentUser);
        return true;
>>>>>>> Feat : add possibility for redactoring
=======
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
>>>>>>> Feat : try to change post to put
    }

}
