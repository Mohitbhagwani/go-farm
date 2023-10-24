package com.acira.gofarm.repository;

import com.acira.gofarm.entity.Crop;
import com.acira.gofarm.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CropRepository extends JpaRepository<Crop, String> {
    Optional<Crop> findByIdAndDeletedAtNull(String id);
    List<Crop> findByUserAndDeletedAtNull(UserInfo user);
}
