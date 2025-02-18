package com.snaptest.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.HashMap;
import java.util.Map;


@Component
public class OpenAIHelper {

    private final String openAiApiKey;

    public OpenAIHelper(String openAiApiKey) {
        this.openAiApiKey = openAiApiKey;
    }

    private final String OPENAI_URL = "https://api.openai.com/v1/completions";

    public String generateTestCasesFromAI(String code, String language) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + openAiApiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-4");
        requestBody.put("prompt", "Generate test cases for the following " + language + " code:\n" + code);
        requestBody.put("max_tokens", 300);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
        ResponseEntity<Map> response = restTemplate.exchange(OPENAI_URL, HttpMethod.POST, request, Map.class);

        if (response.getBody() != null && response.getBody().containsKey("choices")) {
            return response.getBody().get("choices").toString();
        }
        return "Error generating test cases.";
    }
}
