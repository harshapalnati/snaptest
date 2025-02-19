package com.snaptest.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Component
public class OpenAIHelper {

    private final String openAiApiKey;

    public OpenAIHelper(String openAiApiKey) {
        this.openAiApiKey = openAiApiKey;
    }


    private static final String OPENAI_URL = "https://api.openai.com/v1/chat/completions";

    public String generateTestCasesFromAI(String code, String language) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + openAiApiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // ✅ Correct structure for OpenAI chat model
        Map<String, Object> requestBody = Map.of(
                "model", "gpt-4",
                "messages", List.of(
                        Map.of("role", "system", "content", "You are an AI assistant that generates test cases."),
                        Map.of("role", "user", "content", "Generate test cases for the following " + language + " code:\n" + code)
                ),
                "max_tokens", 300
        );

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> response = restTemplate.exchange(OPENAI_URL, HttpMethod.POST, request, Map.class);

        if (response.getBody() != null && response.getBody().containsKey("choices")) {
            List<Map<String, Object>> choices = (List<Map<String, Object>>) response.getBody().get("choices");
            if (!choices.isEmpty()) {
                return (String) ((Map<String, Object>) choices.get(0).get("message")).get("content");
            }
        }
        return "Error generating test cases.";
    }
}
