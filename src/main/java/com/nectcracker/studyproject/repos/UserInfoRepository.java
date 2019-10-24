package com.nectcracker.studyproject.repos;

import com.nectcracker.studyproject.domain.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo,Long> {
}
