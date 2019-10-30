package com.nectcracker.studyproject.repos;

import com.nectcracker.studyproject.domain.User;

public interface UserRepositoryCustom {
    User findByAuthentication();
}
