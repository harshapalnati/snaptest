package com.snaptest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")  // Ensures test-specific configs are applied
class SnaptestApplicationTests {

	@Test
	void contextLoads() {
	}
}
