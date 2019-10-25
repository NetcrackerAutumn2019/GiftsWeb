package com.nectcracker.studyproject.service;


import com.nectcracker.studyproject.domain.UserWishes;
import com.nectcracker.studyproject.repos.UserWishesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserWishesService {
    private final UserWishesRepository userWishesRepository;

    public UserWishesService(UserWishesRepository userWishesRepository) {
        this.userWishesRepository = userWishesRepository;
    }

    public void addUserWishes(UserWishes userWishes){
        userWishesRepository.save(userWishes);
    }
}
