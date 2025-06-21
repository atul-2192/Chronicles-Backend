package com.Chronicles.ValidatorService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ValidatorServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ValidatorServiceApplication.class, args);
	}

}
