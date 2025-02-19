package com.snaptest.service;

import org.springframework.stereotype.Service;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class TestExecutionService {

    private static final String BASE_PATH = System.getProperty("os.name").toLowerCase().contains("win")
            ? "C:\\tmp\\"
            : "/tmp/";

    private static final int THREAD_POOL_SIZE = 3;  // Number of parallel test executions

    /**
     * Runs tests for multiple frameworks (JUnit, PyTest, Mocha) in parallel.
     */
    public String runTests(String language, List<String> testCases) throws Exception {
        String testFilePath = BASE_PATH + "generated_tests." + getFileExtension(language);
        writeTestCasesToFile(testFilePath, testCases);

        ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        Future<String> testResult = executor.submit(() -> executeTestsInDocker(language, testFilePath));
        executor.shutdown();

        return testResult.get(); // Wait for execution to complete & return result
    }

    /**
     * Writes the generated test cases to a file.
     */
    protected void writeTestCasesToFile(String filePath, List<String> testCases) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            for (String testCase : testCases) {
                writer.write(testCase.replace("\r\n", "\n") + "\n");  // Normalize line endings
            }
        }
    }

    /**
     * Runs test cases inside a Docker container based on the language.
     */
    protected String executeTestsInDocker(String language, String filePath) throws IOException, InterruptedException {
        String dockerImage = getDockerImage(language);
        if (dockerImage == null) return "❌ Unsupported language: " + language;

        String volumeMapping = System.getProperty("os.name").toLowerCase().contains("win")
                ? "C:\\tmp:/app"
                : "/tmp:/app";

        ProcessBuilder pb = new ProcessBuilder("docker", "run", "--rm", "-v", volumeMapping, dockerImage, filePath);
        pb.redirectErrorStream(true);
        Process process = pb.start();

        return captureTestOutput(process);
    }

    /**
     * Captures the test output from the executed Docker process.
     */
    private String captureTestOutput(Process process) throws IOException {
        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
        }
        return output.toString();
    }

    /**
     * Returns the file extension based on the programming language.
     */
    private String getFileExtension(String language) {
        return switch (language.toLowerCase()) {
            case "java" -> "java";
            case "python" -> "py";
            case "javascript" -> "js";
            default -> "txt";
        };
    }

    /**
     * Returns the appropriate Docker image for running tests based on the language.
     */
    private String getDockerImage(String language) {
        return switch (language.toLowerCase()) {
            case "java" -> "junit-runner";
            case "python" -> "pytest-runner";
            case "javascript" -> "mocha-runner";
            default -> null;
        };
    }
}
