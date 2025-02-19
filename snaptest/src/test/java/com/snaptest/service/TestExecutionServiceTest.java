package com.snaptest.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TestExecutionServiceTest {
    private TestExecutionService testExecutionService;

    @BeforeEach
    void setUp() {
        testExecutionService = new TestExecutionService();
    }

    @Test
    void testFileWriting() throws IOException {
        String filePath = "test_output.java";
        List<String> testCases = List.of("public class TestExample { @Test public void test() { assertEquals(2, 1+1); } }");

        // Call method
        testExecutionService.writeTestCasesToFile(filePath, testCases);

        // Check if file exists
        File file = new File(filePath);
        assertTrue(file.exists(), "Test file should be created");

        // Cleanup
        file.delete();
    }

    @Test
    void testRunTests_ValidLanguage() throws Exception {
        // Mock valid execution
        TestExecutionService spyService = Mockito.spy(new TestExecutionService());
        doReturn("✅ All tests passed").when(spyService).executeTestsInDocker(any(), any());

        String result = spyService.runTests("java", List.of("public class ExampleTest {}"));
        assertEquals("✅ All tests passed", result);
    }

    @Test
    void testRunTests_InvalidLanguage() throws Exception {
        String result = testExecutionService.runTests("go", List.of("func TestMain() {}"));
        assertEquals("❌ Unsupported language: go", result);
    }
}
