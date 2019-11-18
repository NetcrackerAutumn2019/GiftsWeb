package com.nectcracker.studyproject.repos;

import com.nectcracker.studyproject.domain.Chat;
import com.nectcracker.studyproject.domain.Participants;
import com.nectcracker.studyproject.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface ParticipantsRepository extends JpaRepository<Participants,Long> {
    Set<Participants> findByChat(Chat chat);
    Participants findByUserForChatAndChat(User user, Chat chat);
    Set<Participants> findByUserForChat(User user);
}
