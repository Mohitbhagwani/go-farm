package com.acira.gofarm.service;


import com.acira.gofarm.constant.Status;
import com.acira.gofarm.dto.CropDTO;
import com.acira.gofarm.entity.Crop;
import com.acira.gofarm.entity.MasterCropDetail;
import com.acira.gofarm.entity.User;
import com.acira.gofarm.exception.CustomException;
import com.acira.gofarm.repository.CropRepository;
import com.acira.gofarm.repository.MasterCropDetailRepository;
import com.acira.gofarm.repository.UserRepository;
import com.acira.gofarm.security.service.JwtService;
import io.jsonwebtoken.Claims;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CropDetailService {

    @Autowired
    MasterCropDetailRepository masterCropDetailRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtService jwtService;

    @Autowired
    CropRepository cropRepository;


    ModelMapper modelMapper= new ModelMapper();

    public CropDTO addCropDetail(CropDTO cropDTO) {
        Claims claims = jwtService.userData();
        User user = userRepository.findByIdAndDeletedAtIsNull(claims.get("id").toString()).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND.value(), String.format("User not found with %s", claims.get("id").toString())));
        if (user.getStatus().equals(Status.INACTIVE)) {
            throw new CustomException(HttpStatus.CONFLICT.value(), String.format("User with contact number %s is INACTIVE", user.getContactNumber().toString()));
        }
        Optional<MasterCropDetail> masterCropDetail = Optional.of(new MasterCropDetail());
        Crop crop = new Crop();
        crop.setId(UUID.randomUUID().toString());
        if (cropDTO.getVariety().equalsIgnoreCase("others")) {
            if (cropDTO.getOtherVariety() == null || cropDTO.getOtherVariety().isEmpty() || cropDTO.getOtherVariety().isBlank()) {
                throw new CustomException(HttpStatus.BAD_REQUEST.value(), "Kindly enter valid value for the field Other variety.");
            } else {
                crop.setOtherVariety(cropDTO.getOtherVariety());
            }
        }
        if (cropDTO.getMasterCropId() == null) {
            throw new CustomException(HttpStatus.BAD_REQUEST.value(), "Kindly enter valid crop id");
        } else {

            masterCropDetail = Optional.ofNullable(masterCropDetailRepository.findByIdAndDeletedAtIsNull(cropDTO.getMasterCropId().toString()).orElseThrow(() -> new CustomException(HttpStatus.BAD_REQUEST.value(), "Invalid master crop id entered.")));
        }
        crop.setVariety(cropDTO.getVariety());
        crop.setQuantityHarvested(cropDTO.getQuantity());
        crop.setLotId(cropDTO.getLotId());
        crop.setDistanceRange(cropDTO.getDistanceRange());


        crop.setMasterCrop(masterCropDetail.get());
        crop.setPrice(cropDTO.getPrice());
        crop.setUom(crop.getUom());
        crop.setUser(user.getUserInfo());
        // Save the new Crop entity to the database
        cropRepository.save(crop);
        cropDTO.setId(crop.getId());
        return cropDTO;
    }

    public CropDTO updateCropDetail(UUID cropId, CropDTO cropDTO) {
        Claims claims = jwtService.userData();
        User user = userRepository.findByIdAndDeletedAtIsNull(claims.get("id").toString()).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND.value(), String.format("User not found with %s", claims.get("id").toString())));
        if (user.getStatus().equals(Status.INACTIVE)) {
            throw new CustomException(HttpStatus.CONFLICT.value(), String.format("User with contact number %s is INACTIVE", user.getContactNumber().toString()));
        }
        Optional<MasterCropDetail> masterCropDetail = Optional.of(new MasterCropDetail());

        Crop crop = cropRepository.findByIdAndDeletedAtNull(cropId.toString()).orElseThrow(() -> new CustomException(HttpStatus.BAD_REQUEST.value(), "Invalid crop id entered"));
        if (cropDTO.getVariety().equalsIgnoreCase("others") && cropDTO.getOtherVariety() == null) {
            throw new CustomException(HttpStatus.BAD_REQUEST.value(), "Kindly enter valid value for the field Other variety.");
        }
        if (cropDTO.getMasterCropId() == null) {
            throw new CustomException(HttpStatus.BAD_REQUEST.value(), "Kindly enter valid crop id");
        } else {

            masterCropDetail = Optional.ofNullable(masterCropDetailRepository.findByIdAndDeletedAtIsNull(cropDTO.getMasterCropId().toString()).orElseThrow(() -> new CustomException(HttpStatus.BAD_REQUEST.value(), "Invalid master crop id entered.")));
        }
        crop.setVariety(cropDTO.getVariety());
        crop.setOtherVariety(cropDTO.getOtherVariety());
        crop.setQuantityHarvested(cropDTO.getQuantity());
        crop.setLotId(cropDTO.getLotId());
        crop.setDistanceRange(cropDTO.getDistanceRange());
        crop.setUom(crop.getUom());
        crop.setMasterCrop(masterCropDetail.get());
        crop.setPrice(cropDTO.getPrice());
        cropDTO.setId(crop.getId());
        crop.setUser(user.getUserInfo());
        // Save the new Crop entity to the database
        cropRepository.save(crop);

        return cropDTO;
    }

    public List<CropDTO> getCropDetailByUserId(UUID userId) {
        User user = userRepository.findByIdAndDeletedAtIsNull(String.valueOf(userId)).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND.value(), String.format("User not found with %s",userId.toString())));
        List<Crop> cropList = cropRepository.findByUserAndDeletedAtNull(user.getUserInfo());
        return cropList.stream()
                .map(crop -> modelMapper.map(crop, CropDTO.class))
                .collect(Collectors.toList());
    }

    public CropDTO getCropDetailByCropDetailId(UUID cropDetailId) {
    return modelMapper.map(cropRepository.findByIdAndDeletedAtNull(cropDetailId.toString()), CropDTO.class);
    }
}
