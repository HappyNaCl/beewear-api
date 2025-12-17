package com.beewear.api.application.controllers;

import com.beewear.api.application.services.ClassificationService;
import com.beewear.api.application.services.ClassificationService.ClassificationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/classify")
@CrossOrigin(origins = "*")
public class ClassificationController {

    @Autowired
    private ClassificationService classificationService;

    @PostMapping
    public ResponseEntity<?> classifyImage(@RequestParam("file") MultipartFile file) {
        try {
            // Validate file
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body(errorResponse("Please upload a valid image file."));
            }
            
            // Validate file type
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                return ResponseEntity.badRequest().body(errorResponse("File must be an image (JPEG, PNG, etc.)"));
            }
            
            // Validate file size (max 10MB)
            if (file.getSize() > 10 * 1024 * 1024) {
                return ResponseEntity.badRequest().body(errorResponse("File size must be less than 10MB"));
            }

            // Classify the image
            ClassificationResult result = classificationService.classifyImage(file);
            
            return ResponseEntity.ok(successResponse(result));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(errorResponse("Error processing image: " + e.getMessage()));
        }
    }
    
    @GetMapping("/health")
    public ResponseEntity<?> healthCheck() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "ok");
        response.put("service", "classification");
        return ResponseEntity.ok(response);
    }
    
    private Map<String, Object> successResponse(ClassificationResult result) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("prediction", result.getLabel());
        response.put("confidences", result.getConfidences());
        return response;
    }
    
    private Map<String, Object> errorResponse(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("error", message);
        return response;
    }
}