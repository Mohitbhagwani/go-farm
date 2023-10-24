package com.acira.gofarm.controller;

import com.acira.gofarm.dto.CropDTO;
import com.acira.gofarm.service.CropDetailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/fpo/")
public class CropDetailController {
    @Autowired
    CropDetailService cropDetailService;

    @PreAuthorize("hasAuthority('fpo')")
    @PostMapping("crop")
    public ResponseEntity<CropDTO> addCropDetail(@RequestBody @Valid CropDTO cropDTO) {
        CropDTO crop = cropDetailService.addCropDetail(cropDTO);
        if (crop != null) {
            return new ResponseEntity<>(crop, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('fpo')")
    @PutMapping("crop/{cropId}")
    public ResponseEntity<CropDTO> updateCropDetail(@PathVariable UUID cropId, @RequestBody CropDTO cropDTO) {
        CropDTO crop = cropDetailService.updateCropDetail(cropId, cropDTO);
        if (crop != null) {
            return new ResponseEntity<>(crop, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("user/{userId}")
    public ResponseEntity<Object> getCropDetailByUserId(@PathVariable UUID userId) {
        List<CropDTO> crop = cropDetailService.getCropDetailByUserId(userId);
        return new ResponseEntity<>(crop, HttpStatus.OK);
    }

    @GetMapping("user/{cropDetailId}")
    public ResponseEntity<Object> getCropDetailByCropDetailId(@PathVariable UUID cropDetailId) {
        return new ResponseEntity<>(cropDetailService.getCropDetailByCropDetailId(cropDetailId), HttpStatus.OK);
    }

    @DeleteMapping("user/{cropDetailId}")
    public ResponseEntity<Void> deleteCropDetailByCropDetailId(@PathVariable UUID cropDetailId) {
        cropDetailService.deleteCropDetailByCropDetailId(cropDetailId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
