package com.poly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SanTmdtPetApplication {

	public static void main(String[] args) {
		SpringApplication.run(SanTmdtPetApplication.class, args);
	}

}
