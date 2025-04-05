package com.example.vehicle_transport_calculator.model.dto;



import com.example.vehicle_transport_calculator.model.enums.EngineTypeEnum;
import com.example.vehicle_transport_calculator.model.enums.PortOfDischargeEnum;
import com.example.vehicle_transport_calculator.model.enums.PortOfLoadingEnum;

import java.math.BigDecimal;
import java.util.List;

public record OfferDetailsDTO(Long id,
                              String description,
                              PortOfLoadingEnum portOfLoading,
                              PortOfDischargeEnum portOfDischarge,
                              EngineTypeEnum engineType,
                              Integer price,
                              BigDecimal oceanFreight,
                              List<String> allCurrencies) {

}
