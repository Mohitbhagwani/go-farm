package com.acira.gofarm.repository;



import com.acira.gofarm.entity.Crop;
import com.acira.gofarm.entity.MasterCropDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MasterCropDetailRepository extends JpaRepository<MasterCropDetail, String> {
    Optional<MasterCropDetail> findByIdAndDeletedAtIsNull(String id);
}
