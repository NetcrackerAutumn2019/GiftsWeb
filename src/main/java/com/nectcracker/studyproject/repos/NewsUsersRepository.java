package com.nectcracker.studyproject.repos;

import com.nectcracker.studyproject.domain.News;
import com.nectcracker.studyproject.domain.NewsUsers;
import com.nectcracker.studyproject.domain.NewsUsersId;
import com.nectcracker.studyproject.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface NewsUsersRepository extends JpaRepository<NewsUsers, NewsUsersId> {
    Set<NewsUsers> findAllByUsers(User user);
    Set<NewsUsers> findAllByNewsContains(News news);
    int countAllByUsers(User user);
}
