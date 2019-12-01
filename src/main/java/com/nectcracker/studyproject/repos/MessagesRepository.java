package com.nectcracker.studyproject.repos;

import com.nectcracker.studyproject.domain.Chat;
import com.nectcracker.studyproject.domain.Messages;
import com.nectcracker.studyproject.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface MessagesRepository extends JpaRepository<Messages, Long> {
    Set<Messages> findByChat(Chat chat);
    Set<Messages> findByChatAndAuthor(Chat chat, User author);
}
