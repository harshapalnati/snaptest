package com.snaptest.service;

import com.snaptest.model.SnapTestRequest;
import com.snaptest.model.SnapTestResponse;

public interface SnapTestService
{
    SnapTestResponse generateTestCases(SnapTestRequest request);
}
