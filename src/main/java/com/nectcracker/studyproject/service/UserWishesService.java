package com.nectcracker.studyproject.service;


import com.nectcracker.studyproject.domain.UserWishes;
import com.nectcracker.studyproject.repos.UserWishesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserWishesService {
    @Autowired
    private UserWishesRepository userWishesRepository;

    public void addUserWishes(UserWishes userWishes){
        userWishesRepository.save(userWishes);
    }
}
