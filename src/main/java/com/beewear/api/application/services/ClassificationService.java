package com.beewear.api.application.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClassificationService {

    private final String HF_API_BASE = "https://reinhartgw-clothing-classifier.hf.space/gradio_api/call/classify_image";
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ClassificationResult classifyImage(MultipartFile file) throws IOException {
        byte[] fileContent = file.getBytes();
        String encodedString = Base64.getEncoder().encodeToString(fileContent);
        
        String mimeType = file.getContentType() != null ? file.getContentType() : "image/jpeg";
        String dataUri = "data:" + mimeType + ";base64," + encodedString;
        
        Map<String, Object> imageData = new HashMap<>();
        imageData.put("url", dataUri);
        
        Map<String, String> meta = new HashMap<>();
        meta.put("_type", "gradio.FileData");
        imageData.put("meta", meta);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("data", Collections.singletonList(imageData));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
        
        Map<String, Object> submitResponse = restTemplate.postForObject(HF_API_BASE, entity, Map.class);
        
        if (submitResponse == null || !submitResponse.containsKey("event_id")) {
            throw new RuntimeException("Failed to get event_id from Gradio API");
        }
        
        String eventId = (String) submitResponse.get("event_id");
        
        String resultUrl = HF_API_BASE + "/" + eventId;
        String sseResponse = pollForResult(resultUrl, 10, 1000); // 10 retries, 1 second apart
        
        return parseSSEResponse(sseResponse);
    }
    
    private String pollForResult(String url, int maxRetries, int delayMs) {
        for (int i = 0; i < maxRetries; i++) {
            try {
                String response = restTemplate.getForObject(url, String.class);
                if (response != null && response.contains("\"data\"")) {
                    return response;
                }
                Thread.sleep(delayMs);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Polling interrupted", e);
            } catch (Exception e) {
                if (i == maxRetries - 1) {
                    throw new RuntimeException("Failed to get result after " + maxRetries + " retries", e);
                }
            }
        }
        throw new RuntimeException("Timeout waiting for classification result");
    }
    
    private ClassificationResult parseSSEResponse(String sseResponse) throws IOException {
        for (String line : sseResponse.split("\n")) {
            if (line.startsWith("data:")) {
                String jsonData = line.substring(5).trim();
                
                if (jsonData.isEmpty() || jsonData.equals("null")) {
                    continue;
                }
                
                JsonNode rootNode = objectMapper.readTree(jsonData);
                
                if (rootNode.isArray() && rootNode.size() > 0) {
                    JsonNode resultNode = rootNode.get(0);
                    
                    String label = resultNode.has("label") ? resultNode.get("label").asText() : "Unknown";
                    
                    List<ConfidenceScore> confidences = new ArrayList<>();
                    if (resultNode.has("confidences") && resultNode.get("confidences").isArray()) {
                        for (JsonNode conf : resultNode.get("confidences")) {
                            String confLabel = conf.get("label").asText();
                            double confScore = conf.get("confidence").asDouble();
                            confidences.add(new ConfidenceScore(confLabel, confScore));
                        }
                    }
                    
                    return new ClassificationResult(label, confidences);
                }
            }
        }
        
        throw new RuntimeException("Could not parse classification result from response");
    }
    
    public static class ClassificationResult {
        private String label;
        private List<ConfidenceScore> confidences;
        
        public ClassificationResult(String label, List<ConfidenceScore> confidences) {
            this.label = label;
            this.confidences = confidences;
        }
        
        public String getLabel() { return label; }
        public void setLabel(String label) { this.label = label; }
        public List<ConfidenceScore> getConfidences() { return confidences; }
        public void setConfidences(List<ConfidenceScore> confidences) { this.confidences = confidences; }
    }
    
    public static class ConfidenceScore {
        private String label;
        private double confidence;
        
        public ConfidenceScore(String label, double confidence) {
            this.label = label;
            this.confidence = confidence;
        }
        
        public String getLabel() { return label; }
        public void setLabel(String label) { this.label = label; }
        public double getConfidence() { return confidence; }
        public void setConfidence(double confidence) { this.confidence = confidence; }
    }
}