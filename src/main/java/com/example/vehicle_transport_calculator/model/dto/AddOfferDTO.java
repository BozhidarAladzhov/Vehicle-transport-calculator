package com.example.vehicle_transport_calculator.model.dto;


import com.example.vehicle_transport_calculator.model.enums.EngineTypeEnum;
import com.example.vehicle_transport_calculator.model.enums.PortOfDischargeEnum;
import com.example.vehicle_transport_calculator.model.enums.PortOfLoadingEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record AddOfferDTO(
    @NotNull(message = "{add.offer.description.length}")
    @Size(message = "{add.offer.description.length}",
        min = 5,
        max = 500) String description,//not necessarily from message source
    @NotNull PortOfLoadingEnum portOfLoading,
    @NotNull PortOfDischargeEnum portOfDischarge,
    @NotNull EngineTypeEnum engineType,
    @NotNull @PositiveOrZero Integer price
) {

  public static AddOfferDTO empty() {
    return new AddOfferDTO(null, null, null, null, null);
  }

}
