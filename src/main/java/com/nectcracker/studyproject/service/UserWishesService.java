package com.nectcracker.studyproject.service;

import com.nectcracker.studyproject.domain.User;
import com.nectcracker.studyproject.domain.UserWishes;
import com.nectcracker.studyproject.repos.UserRepository;
import com.nectcracker.studyproject.repos.UserWishesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserWishesService {
    @Autowired
    private UserWishesRepository userWishesRepository;

    @Autowired
    private UserRepository userRepository;

    public String showWishes(Map<String, Object> model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.findByUsername(auth.getName());
        Iterable<UserWishes> messages = userWishesRepository.findByUserId(currentUser.getId());
        model.put("messages", messages);
        return "new_wish";
    }

    public boolean addWish(String text, Map<String, Object> model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.findByUsername(auth.getName());
        UserWishes m = new UserWishes(currentUser, text);
        userWishesRepository.save(m);
        return true;
    }
}
