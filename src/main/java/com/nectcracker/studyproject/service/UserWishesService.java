package com.nectcracker.studyproject.service;

import com.nectcracker.studyproject.domain.User;
import com.nectcracker.studyproject.domain.UserWishes;
import com.nectcracker.studyproject.repos.UserRepository;
import com.nectcracker.studyproject.repos.UserRepositoryCustom;
import com.nectcracker.studyproject.repos.UserWishesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserWishesService implements UserRepositoryCustom {
    private final UserWishesRepository userWishesRepository;

    private final UserRepository userRepository;

    public UserWishesService(UserWishesRepository userWishesRepository, UserRepository userRepository) {
        this.userWishesRepository = userWishesRepository;
        this.userRepository = userRepository;
    }

    @Override
    public User findByAuthentication() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUsername(auth.getName());
    }

    public Iterable<UserWishes> getUserWishes() {
        User currentUser = findByAuthentication();
        Iterable<UserWishes> messages = userWishesRepository.findByUserId(currentUser.getId());
        return messages;
    }

    public boolean addWish(String text) {
        try {
            User currentUser = findByAuthentication();
            UserWishes m = new UserWishes(currentUser, text);
            userWishesRepository.save(m);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
