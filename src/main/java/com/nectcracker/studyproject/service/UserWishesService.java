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
import sun.rmi.runtime.Log;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    public Iterable<UserWishes> getUserWishes(User user) {
        return userWishesRepository.findByUserId(user.getId());
    }

    public boolean addWish(String text) {
        try {
            User currentUser = findByAuthentication();
            UserWishes m = new UserWishes(currentUser, text);
            m.setFriendCreateWish(false);
            userWishesRepository.save(m);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean addWishfromFriend(User user, String wishName){
        try {
            UserWishes m = new UserWishes(user, wishName);
            m.setFriendCreateWish(true);
            userWishesRepository.save(m);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public UserWishes getById(Long id) {
        Optional<UserWishes> wish = userWishesRepository.findById(id);
        return wish.orElseGet(UserWishes::new);
    }
}
