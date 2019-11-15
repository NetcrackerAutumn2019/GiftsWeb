package com.nectcracker.studyproject.repos;


import com.nectcracker.studyproject.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.jws.soap.SOAPBinding;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByActivationCode(String code);
    User findByEmail(String email);
    User findByVkId(Long id);
    Set<User> findAllByFriends(User user);
}
