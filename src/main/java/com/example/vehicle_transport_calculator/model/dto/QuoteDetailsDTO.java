package com.example.vehicle_transport_calculator.model.dto;



import com.example.vehicle_transport_calculator.model.enums.EngineTypeEnum;
import com.example.vehicle_transport_calculator.model.enums.PortOfDischargeEnum;
import com.example.vehicle_transport_calculator.model.enums.PortOfLoadingEnum;

import java.util.List;

public record QuoteDetailsDTO(Long id,
                              String description,
                              EngineTypeEnum engineType,
                              PortOfLoadingEnum portOfLoading,
                              PortOfDischargeEnum portOfDischarge,
                              List<String> allCurrencies) {

}
