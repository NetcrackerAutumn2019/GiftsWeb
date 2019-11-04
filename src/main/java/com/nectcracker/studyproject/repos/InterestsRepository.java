package com.nectcracker.studyproject.repos;

import com.nectcracker.studyproject.domain.Interests;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterestsRepository extends JpaRepository<Interests, Long> {
    Interests findByInterestName(String InterestName);
}
