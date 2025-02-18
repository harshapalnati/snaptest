package com.snaptest.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.snaptest.model.SnapTestRequest;
import com.snaptest.model.SnapTestResponse;
import com.snaptest.service.SnapTestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SnapTestController.class)
@ExtendWith(MockitoExtension.class)  // NEW: Use Mockito Extension instead of @MockBean
class SnapTestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private SnapTestService snapTestService;  // NEW: Replace @MockBean with @Mock

    @InjectMocks
    private SnapTestController snapTestController; // NEW: Inject Mock Service into Controller

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        when(snapTestService.generateTestCases(any(SnapTestRequest.class)))
                .thenReturn(new SnapTestResponse("Generated test cases"));
    }

    @Test
    void testGenerateTestCases() throws Exception {
        SnapTestRequest request = new SnapTestRequest("def add(a, b): return a + b", "Python");

        mockMvc.perform(post("/api/v1/testgen/generate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.testCases").value("Generated test cases"));
    }
}
