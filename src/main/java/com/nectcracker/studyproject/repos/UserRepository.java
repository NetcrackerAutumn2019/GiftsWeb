package com.nectcracker.studyproject.repos;


import com.nectcracker.studyproject.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByActivationCode(String code);
    User findByEmail(String email);
    User findByVkId(Long id);
}
