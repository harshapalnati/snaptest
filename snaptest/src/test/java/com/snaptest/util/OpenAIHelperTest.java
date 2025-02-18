package com.snaptest.util;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class OpenAIHelperTest {

    @InjectMocks
    private OpenAIHelper openAIHelper;

    @Mock
    private String openAiApiKey;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        openAiApiKey = "test-api-key";
        openAIHelper = new OpenAIHelper(openAiApiKey);
    }

    @Test
    void testGenerateTestCasesFromAI() {
        String code = "def add(a, b): return a + b";
        String language = "Python";
        String response = openAIHelper.generateTestCasesFromAI(code, language);

        assertNotNull(response);
        assertTrue(response.contains("Generated test cases using AI"));
    }
}
