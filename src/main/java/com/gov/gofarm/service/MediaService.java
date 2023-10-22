package com.gov.gofarm.service;

import org.springframework.stereotype.Service;
import java.util.Base64;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class MediaService {

    // Simulated in-memory storage for Base64 data
    private Map<Long, String> mediaDataStore = new HashMap<>();
    private Long nextMediaId = 1L;

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

    public void saveMedia(String base64Data) {
        long mediaId = nextMediaId++;
        mediaDataStore.put(mediaId, base64Data);
    }

    public String getMediaById(Long mediaId) {
        return mediaDataStore.get(mediaId);
    }

    public byte[] convertBase64ToFile(String base64Data) {
        Base64.Decoder decoder = Base64.getDecoder();
        return decoder.decode(base64Data);
    }
}
