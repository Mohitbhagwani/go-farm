package com.acira.gofarm.repository;

import com.acira.gofarm.entity.MasterCropDetail;
import com.acira.gofarm.entity.MasterTalukDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MasterTalukDetailRepository extends JpaRepository<MasterTalukDetail, String> {
    Optional<MasterTalukDetail> findByIdAndDeletedAtIsNull(String id);
}

