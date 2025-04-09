package com.example.vehicle_transport_calculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class VehicleTransportCalculatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(VehicleTransportCalculatorApplication.class, args);
	}

}
