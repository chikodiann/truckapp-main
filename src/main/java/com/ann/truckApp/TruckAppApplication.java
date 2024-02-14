package com.ann.truckApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TruckAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(TruckAppApplication.class, args);
	}

}
