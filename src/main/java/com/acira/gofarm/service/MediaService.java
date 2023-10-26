package com.acira.gofarm.service;

import com.acira.gofarm.constant.Status;
import com.acira.gofarm.entity.Crop;
import com.acira.gofarm.entity.Media;
import com.acira.gofarm.entity.UserInfo;
import com.acira.gofarm.exception.CustomException;
import com.acira.gofarm.repository.CropRepository;
import com.acira.gofarm.repository.MediaRepository;
import com.acira.gofarm.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class MediaService {

    @Autowired
    MediaRepository mediaRepository;

    @Autowired
    UserInfoRepository userInfoRepository;

    @Autowired
    CropRepository cropRepository;

    public String convertFileToBase64(MultipartFile file) {
        try {
            byte[] fileBytes = file.getBytes();
            Base64.Encoder encoder = Base64.getEncoder();
            String base64Data = encoder.encodeToString(fileBytes);
            return base64Data;
        } catch (IOException e) {
            throw new RuntimeException("Failed to convert file to Base64.", e);
        }
    }

    public Map<String, Object> uploadUserDataByUserId(String userId, MultipartFile file, String mediaType) {
        UserInfo user = userInfoRepository.findByIdAndDeletedAtIsNull(userId).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND.value(), String.format("User not found with %s", userId)));
        Media media = new Media();
        media.setId(UUID.randomUUID().toString());
        media.setFileName(file.getOriginalFilename());
        media.setMediaType(mediaType);
        media.setMediaData(convertFileToBase64(file));
        media.setStatus(Status.ACTIVE);
        mediaRepository.save(media);
        user.setMedia(media);
        userInfoRepository.save(user);
        Map<String, Object> mediaResponse = new HashMap<>();
        mediaResponse.put("fileName", media.getFileName());
        mediaResponse.put("mediaType", media.getMediaType());
        mediaResponse.put("userId", user.getId());
        mediaResponse.put("mediaId", media.getId());
        mediaResponse.put("message", "Media uploaded successfully.");
        return mediaResponse;
    }

    public Map<String, Object> uploadUserDataByCropId(String userId, MultipartFile file, String mediaType) {
        Crop crop = cropRepository.findByIdAndDeletedAtIsNull(userId).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND.value(), String.format("crop detail not found with %s", userId)));
        Media media = new Media();
        media.setId(UUID.randomUUID().toString());
        media.setFileName(file.getName());
        media.setMediaType(mediaType);
        media.setMediaData(convertFileToBase64(file));
        media.setStatus(Status.ACTIVE);
        mediaRepository.save(media);
        crop.setMedia(media);
        cropRepository.save(crop);
        Map<String, Object> mediaResponse = new HashMap<>();
        mediaResponse.put("fileName", media.getFileName());
        mediaResponse.put("mediaType", media.getMediaType());
        mediaResponse.put("cropId", crop.getId());
        mediaResponse.put("mediaId", media.getId());
        mediaResponse.put("message", "Media uploaded successfully.");
        return mediaResponse;
    }

    public Media getMediaByMediaId(String mediaId) {
        return Optional.ofNullable(mediaRepository.findByIdAndDeletedAtNull(mediaId)).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND.value(), String.format("Media id %s not found", mediaId)));
    }

    public byte[] convertBase64ToFile(String base64Data) {
        Base64.Decoder decoder = Base64.getDecoder();
        return decoder.decode(base64Data);
    }
}
