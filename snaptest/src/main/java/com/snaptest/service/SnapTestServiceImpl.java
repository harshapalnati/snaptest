package com.snaptest.service;

import com.snaptest.model.SnapTestRequest;
import com.snaptest.model.SnapTestResponse;
import com.snaptest.util.OpenAIHelper;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SnapTestServiceImpl implements SnapTestService {
    private final OpenAIHelper openAIHelper;

    public SnapTestServiceImpl(OpenAIHelper openAIHelper) {
        this.openAIHelper = openAIHelper;
    }

    @Override
    public SnapTestResponse generateTestCases(SnapTestRequest request) {
        List<String> testCasesList = openAIHelper.generateTestCasesFromAI(request.getCode(), request.getLanguage());

        // Convert List<String> to a single String with newline separation
        String testCases = String.join("\n", testCasesList);

        return new SnapTestResponse(testCases);
    }
}
