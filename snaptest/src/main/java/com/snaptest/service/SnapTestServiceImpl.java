package com.snaptest.service;

import com.snaptest.model.SnapTestRequest;
import com.snaptest.model.SnapTestResponse;
import com.snaptest.util.OpenAIHelper;
import org.springframework.stereotype.Service;


@Service
public class SnapTestServiceImpl implements SnapTestService {
    private final OpenAIHelper openAIHelper;

    public SnapTestServiceImpl(OpenAIHelper openAIHelper) {
        this.openAIHelper = openAIHelper;
    }

    @Override
    public SnapTestResponse generateTestCases(SnapTestRequest request) {
        String testCases = openAIHelper.generateTestCasesFromAI(request.getCode(), request.getLanguage());
        return new SnapTestResponse(testCases);
    }
}
