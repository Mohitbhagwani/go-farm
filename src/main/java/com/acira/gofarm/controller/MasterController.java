package com.acira.gofarm.controller;

import com.acira.gofarm.entity.MasterCropDetail;
import com.acira.gofarm.entity.MasterTalukDetail;
import com.acira.gofarm.repository.MasterCropDetailRepository;
import com.acira.gofarm.repository.MasterTalukDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/masters/")
public class MasterController {
    @Autowired
    MasterTalukDetailRepository masterTalukDetailRepository;
    @Autowired
    MasterCropDetailRepository masterCropDetailRepository;

    @GetMapping("talukas")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<MasterTalukDetail> getAllTalukasDetails(){
        return masterTalukDetailRepository.findAll();
    }


    @GetMapping("crops")
    public List<MasterCropDetail> getAllCrops(){
        return masterCropDetailRepository.findAll();
    }
}
