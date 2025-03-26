package com.example.vehicle_transport_calculator.service;

import com.example.vehicle_transport_calculator.model.dto.ExRateDTO;

public interface KafkaPublicationService {

    void publishExRate(ExRateDTO exRateDTO);
}
