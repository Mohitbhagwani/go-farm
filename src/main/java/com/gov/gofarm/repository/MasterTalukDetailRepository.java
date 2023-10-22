package com.gov.gofarm.repository;

import com.gov.gofarm.entity.MasterTalukDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MasterTalukDetailRepository extends JpaRepository<MasterTalukDetail, String> {

}

