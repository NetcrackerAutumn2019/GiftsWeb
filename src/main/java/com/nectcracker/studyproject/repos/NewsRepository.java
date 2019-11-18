package com.nectcracker.studyproject.repos;

import com.nectcracker.studyproject.domain.News;
import com.nectcracker.studyproject.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface NewsRepository extends JpaRepository<News, Long> {

    //Set<News> findAllByUsersNewsContains(User user);

}
