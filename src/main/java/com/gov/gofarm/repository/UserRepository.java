package com.gov.gofarm.repository;


import com.gov.gofarm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByEmailIgnoreCase(String email);


    User findByContactNumber(Long contactNumber);


}
