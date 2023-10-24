package com.acira.gofarm.repository;


import com.acira.gofarm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, String> {
    User findByContactNumberAndDeletedAtIsNull(Long contactNumber);

    Optional<User> findByIdAndDeletedAtIsNull(String contactNumber);

}
