package com.snaptest.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.snaptest.model.SnapTestRequest;
import com.snaptest.model.SnapTestResponse;
import com.snaptest.util.OpenAIHelper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class SnapTestServiceTest {

    @InjectMocks
    private SnapTestServiceImpl snapTestService;

    @Mock
    private OpenAIHelper openAIHelper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        snapTestService = new SnapTestServiceImpl(openAIHelper);
    }

    @Test
    void testGenerateTestCases() {
        SnapTestRequest request = new SnapTestRequest("def add(a, b): return a + b", "Python");
        when(openAIHelper.generateTestCasesFromAI(anyString(), anyString())).thenReturn("Generated test cases");

        SnapTestResponse response = snapTestService.generateTestCases(request);

        assertNotNull(response);
        assertEquals("Generated test cases", response.getTestCases());
    }
}

