package com.nectcracker.studyproject.repos;

import com.nectcracker.studyproject.domain.Chat;
import com.nectcracker.studyproject.domain.Messages;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessagesRepository extends JpaRepository<Messages, Long> {
    List<Messages> findByChat(Chat chat);
}
