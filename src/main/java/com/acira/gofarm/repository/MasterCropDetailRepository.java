package com.acira.gofarm.repository;



import com.acira.gofarm.entity.MasterCropDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MasterCropDetailRepository extends JpaRepository<MasterCropDetail, String> {

}
