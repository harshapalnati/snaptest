package com.snaptest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.snaptest", "com.snaptest.service.impl"})  // ✅ Ensures services are scanned
public class SnaptestApplication {
	public static void main(String[] args) {
		SpringApplication.run(SnaptestApplication.class, args);
	}
}
