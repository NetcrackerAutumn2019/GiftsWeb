package com.nectcracker.studyproject.repos;

import com.nectcracker.studyproject.domain.UserWishes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserWishesRepository extends JpaRepository<UserWishes,Long> {
}
