package authentification.service;

import authentification.domain.UserInfo;
import authentification.repos.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoService {
    @Autowired
    private UserInfoRepository userInfoRepository;

    public void addUserInfo(UserInfo userInfo){
        userInfoRepository.save(userInfo);
    }
}
