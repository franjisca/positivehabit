package com.side.positivehabit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PositivehabitApplication {

	public static void main(String[] args) {
		SpringApplication.run(PositivehabitApplication.class, args);
	}

}
