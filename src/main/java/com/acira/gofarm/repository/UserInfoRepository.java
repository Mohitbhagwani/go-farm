package com.acira.gofarm.repository;

import com.acira.gofarm.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, String> {
    Optional<UserInfo> findByIdAndDeletedAtIsNull(String contactNumber);
}
