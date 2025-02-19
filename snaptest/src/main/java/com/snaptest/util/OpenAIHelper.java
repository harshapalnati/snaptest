package com.snaptest.util;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OpenAIHelper {

    private final String openAiApiKey;

    public OpenAIHelper(String openAiApiKey) {
        this.openAiApiKey = openAiApiKey;
    }

    private final String OPENAI_URL = "https://api.openai.com/v1/chat/completions";

    public List<String> generateTestCasesFromAI(String code, String language) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + openAiApiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-4");
        requestBody.put("messages", List.of(
                Map.of("role", "system", "content", "You are an expert software tester."),
                Map.of("role", "user", "content", "Generate unit test cases for this " + language + " code:\n" + code)
        ));
        requestBody.put("max_tokens", 500);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
        ResponseEntity<Map> response = restTemplate.exchange(OPENAI_URL, HttpMethod.POST, request, Map.class);

        if (response.getBody() != null && response.getBody().containsKey("choices")) {
            List<Map<String, Object>> choices = (List<Map<String, Object>>) response.getBody().get("choices");
            String testCases = (String) choices.get(0).get("message");

            return List.of(testCases.split("\n"));  // Split into individual test cases
        }
        return List.of("Error generating test cases.");
    }
}
