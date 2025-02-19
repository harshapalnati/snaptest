package com.snaptest.api;

import com.snaptest.model.SnapTestRequest;
import com.snaptest.model.SnapTestResponse;
import com.snaptest.service.SnapTestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/testgen")
@Tag(name = "Test Case Generator", description = "API to generate test cases from code using AI")
public class SnapTestController {

    private final SnapTestService snapTestService;

    public SnapTestController(SnapTestService snapTestService) {
        this.snapTestService = snapTestService;
    }

    @PostMapping("/generate")
    @Operation(summary = "Generate Test Cases", description = "Takes source code and language as input and returns AI-generated test cases.")
    public ResponseEntity<SnapTestResponse> generateTestCases(@RequestBody SnapTestRequest request) {
        SnapTestResponse response = snapTestService.generateTestCases(request);
        return ResponseEntity.ok(response);
    }
}
