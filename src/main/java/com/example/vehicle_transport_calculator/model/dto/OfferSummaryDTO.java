package com.example.vehicle_transport_calculator.model.dto;


import com.example.vehicle_transport_calculator.model.enums.EngineTypeEnum;

public record OfferSummaryDTO(Long id,
                              String description,
                              Integer mileage,
                              EngineTypeEnum engineType) {

}
