package com.snaptest.model;

public class SnapTestResponse {
    private String testCases;

    public SnapTestResponse() {}

    public SnapTestResponse(String testCases) {
        this.testCases = testCases;
    }

    public String getTestCases() {
        return testCases;
    }

    public void setTestCases(String testCases) {
        this.testCases = testCases;
    }
}
