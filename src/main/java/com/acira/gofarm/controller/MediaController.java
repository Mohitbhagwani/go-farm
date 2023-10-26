package com.acira.gofarm.controller;

import com.acira.gofarm.dto.http.ApiResponse;
import com.acira.gofarm.entity.Media;
import com.acira.gofarm.exception.CustomException;
import com.acira.gofarm.repository.UserRepository;
import com.acira.gofarm.security.service.JwtService;
import com.acira.gofarm.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.UUID;

@Controller
@RequestMapping("/api/media")
public class MediaController {

    @Autowired
    private MediaService mediaService;


    @Autowired
    JwtService jwtService;


    @Autowired
    UserRepository userRepository;

    @PostMapping("/upload/user/{userId}")
    public ResponseEntity<Object> uploadUserDataByUserId(@RequestParam("file") MultipartFile file, @PathVariable("userId") UUID userId, @RequestParam("mediaType") String mediaType) {
        return ResponseEntity.ok(new ApiResponse<>(true, mediaService.uploadUserDataByUserId(userId.toString(),file, mediaType), null));
    }

    @GetMapping("/download/{mediaId}")
    public ResponseEntity<byte[]> downloadMedia(@PathVariable UUID mediaId) {
        Media media= mediaService.getMediaByMediaId(mediaId.toString());
        if (media.getMediaData() != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", media.getFileName()); // You can set the filename here.
            byte[] mediaBytes = mediaService.convertBase64ToFile(media.getMediaData());
            return new ResponseEntity<>(mediaBytes, headers, HttpStatus.OK);
        } else {
            throw new CustomException(HttpStatus.NOT_FOUND.value(), "Media not found");
        }
    }

    @PostMapping("/upload/crop/{cropId}")
    public ResponseEntity<Object> uploadUserDataByCropId(@RequestParam("file") MultipartFile file, @PathVariable("cropId") UUID cropId, @RequestParam("mediaType") String mediaType) {
        return ResponseEntity.ok(new ApiResponse<>(true, mediaService.uploadUserDataByCropId(cropId.toString(),file, mediaType), null));
    }

}
