package com.example.vehicle_transport_calculator.service;



import com.example.vehicle_transport_calculator.model.dto.AddOfferDTO;
import com.example.vehicle_transport_calculator.model.dto.OfferDetailsDTO;
import com.example.vehicle_transport_calculator.model.dto.OfferSummaryDTO;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface OfferService {

  void createOffer(AddOfferDTO addOfferDTO);

  void deleteOffer(Long offerId);

  OfferDetailsDTO getOfferDetails(Long id);

  List<OfferSummaryDTO> getAllOffersSummary();
}
