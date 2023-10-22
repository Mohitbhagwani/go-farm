package com.gov.gofarm.controller;

import com.gov.gofarm.dto.http.ApiResponse;
import com.gov.gofarm.exception.CustomException;
import com.gov.gofarm.repository.UserRepository;
import com.gov.gofarm.security.service.JwtService;
import com.gov.gofarm.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/api/media")
public class MediaController {

    @Autowired
    private MediaService mediaService;


    @Autowired
    JwtService jwtService;


    @Autowired
    UserRepository userRepository;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<String>> uploadMedia(@RequestParam("file") MultipartFile file) {

        String base64Data = mediaService.convertFileToBase64(file);
        mediaService.saveMedia(base64Data);
        return ResponseEntity.ok(new ApiResponse<>(true, "Media uploaded successfully", null));
    }

    @GetMapping("/download/{mediaId}")
    public ResponseEntity<ApiResponse<byte[]>> downloadMedia(@PathVariable Long mediaId) {
        String base64Data = mediaService.getMediaById(mediaId);

        if (base64Data != null) {
            byte[] mediaBytes = mediaService.convertBase64ToFile(base64Data);
            return ResponseEntity.ok(new ApiResponse<>(true, mediaBytes, null));
        } else {
            throw new CustomException(HttpStatus.NOT_FOUND.value(), "Media not found");
        }
    }


}
