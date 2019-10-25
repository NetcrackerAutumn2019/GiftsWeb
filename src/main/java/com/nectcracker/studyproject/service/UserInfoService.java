package com.nectcracker.studyproject.service;

import com.nectcracker.studyproject.domain.UserInfo;
import com.nectcracker.studyproject.repos.UserInfoRepository;
import com.nectcracker.studyproject.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoService {
    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private UserRepository userRepository;

    public boolean addUserInfo(UserInfo userInfo) {
        userInfo.setUser(userInfo.getUser());
        userInfo.setFirstName(userInfo.getFirstName());
        userInfo.setLastName(userInfo.getLastName());
        userInfo.setEmail(userInfo.getEmail());
        userInfo.setBirthday(userInfo.getBirthday());
        userInfoRepository.save(userInfo);
        return true;
    }
}
