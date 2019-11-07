package com.nectcracker.studyproject.repos;

import com.nectcracker.studyproject.domain.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {

}
