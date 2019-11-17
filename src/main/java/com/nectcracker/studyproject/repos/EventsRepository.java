package com.nectcracker.studyproject.repos;

import com.nectcracker.studyproject.domain.Events;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventsRepository extends JpaRepository<Events, Long> {
}
