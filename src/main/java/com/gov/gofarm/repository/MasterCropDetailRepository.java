package com.gov.gofarm.repository;



import com.gov.gofarm.entity.MasterCropDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MasterCropDetailRepository extends JpaRepository<MasterCropDetail, String> {

}
