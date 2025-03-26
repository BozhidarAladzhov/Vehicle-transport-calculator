package com.example.vehicle_transport_calculator.service;

import com.example.vehicle_transport_calculator.model.dto.ExRatesDTO;

import java.math.BigDecimal;
import java.util.List;

public interface ExRateService {

    List<String> allSupportedCurrencies();
    boolean hasInitializedExRates();

    ExRatesDTO fetchExRates();

    void updateRates(ExRatesDTO exRatesDTO);

    BigDecimal convert(String from, String to, BigDecimal amount);

    void publishExRates();
}
