package com.enviro.assessment.grad001.TebogoPhiri;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class WasteSortingApplication {

	// Logger to output important information about the application's state and events
	private static final Logger log = LoggerFactory.getLogger(WasteSortingApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(WasteSortingApplication.class, args);
		log.info("Waste Sorting Application started successfully");
	}

}
