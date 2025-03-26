package com.example.vehicle_transport_calculator.init;

import com.example.vehicle_transport_calculator.service.ExRateService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(0)
@Component
public class ExRatesPublisher implements CommandLineRunner {

    private final ExRateService exRateService;

    public ExRatesPublisher(ExRateService exRateService) {
        this.exRateService = exRateService;
    }

    @Override
    public void run(String... args) throws Exception {
        exRateService.publishExRates();
    }
}
