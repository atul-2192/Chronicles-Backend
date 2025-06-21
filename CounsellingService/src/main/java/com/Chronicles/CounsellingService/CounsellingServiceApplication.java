package com.Chronicles.CounsellingService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class CounsellingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CounsellingServiceApplication.class, args);
	}

}
