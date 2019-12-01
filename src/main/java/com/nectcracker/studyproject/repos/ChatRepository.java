package com.nectcracker.studyproject.repos;

import com.nectcracker.studyproject.domain.Chat;
import com.nectcracker.studyproject.domain.Participants;
import com.nectcracker.studyproject.domain.User;
import com.nectcracker.studyproject.domain.UserWishes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    Chat findByWishForChat(UserWishes wishForChat);
    Chat findByOwner(User user);
    Set<Chat> findByChatForParticipants(Participants participants);
    void deleteById(Long id);
}
