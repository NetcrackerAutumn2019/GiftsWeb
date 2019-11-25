package com.nectcracker.studyproject.service;

import com.nectcracker.studyproject.domain.Interests;
import com.nectcracker.studyproject.domain.User;
import com.nectcracker.studyproject.repos.InterestsRepository;
import com.nectcracker.studyproject.repos.UserInfoRepository;
import com.nectcracker.studyproject.repos.UserRepository;
import com.nectcracker.studyproject.repos.UserRepositoryCustom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class InterestsService implements UserRepositoryCustom {
    private final InterestsRepository interestsRepository;
    private final UserRepository userRepository;

    public InterestsService(InterestsRepository interestsRepository, UserRepository userRepository) {
        this.interestsRepository = interestsRepository;
        this.userRepository = userRepository;
    }

    @Override
    public User findByAuthentication() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUsername(auth.getName());
    }

    public void updateUserInterests(String interest) {
        User currentUser = findByAuthentication();
        Interests tmpInterest = interestsRepository.findByInterestName(interest);
        if (tmpInterest != null) {
            currentUser.getInterestsSet().add(tmpInterest);
            userRepository.save(currentUser);
        } else {
            Interests newInterest = new Interests(interest);
            interestsRepository.save(newInterest);
            currentUser.getInterestsSet().add(newInterest);
            userRepository.save(currentUser);
        }
    }

    public Set<Interests> getUserInterests() {
        User currentUser = findByAuthentication();
        return currentUser.getInterestsSet();
    }

    public Set<Interests> getSmbInterests(User user) {
        return user.getInterestsSet();
    }

    public boolean deleteInterest(String name) {
        User currentUser = findByAuthentication();
        boolean isRemoved =  currentUser.getInterestsSet().removeIf(interest -> interest.getInterestName().equals(name));
        userRepository.save(currentUser);
        return isRemoved;
    }

    public List<Interests> getAllInterests() {
       return interestsRepository.findAll();
    }
}
