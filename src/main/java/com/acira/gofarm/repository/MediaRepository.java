package com.acira.gofarm.repository;

import com.acira.gofarm.entity.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaRepository extends JpaRepository<Media,String> {
    Media findByIdAndDeletedAtNull(String mediaId);
}
