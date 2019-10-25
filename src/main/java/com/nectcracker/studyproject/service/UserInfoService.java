package com.nectcracker.studyproject.service;

import com.nectcracker.studyproject.domain.UserInfo;
import com.nectcracker.studyproject.repos.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoService {
    private final UserInfoRepository userInfoRepository;

    public UserInfoService(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    public void addUserInfo(UserInfo userInfo){
        userInfoRepository.save(userInfo);
    }
}
