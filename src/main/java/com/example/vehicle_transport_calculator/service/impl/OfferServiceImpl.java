package com.example.vehicle_transport_calculator.service.impl;


import com.example.vehicle_transport_calculator.model.dto.AddOfferDTO;
import com.example.vehicle_transport_calculator.model.dto.OfferDetailsDTO;
import com.example.vehicle_transport_calculator.model.dto.OfferSummaryDTO;
import com.example.vehicle_transport_calculator.repo.OfferRepository;
import com.example.vehicle_transport_calculator.service.ExRateService;
import com.example.vehicle_transport_calculator.service.OfferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;



import java.util.List;

@Service
public class OfferServiceImpl implements OfferService {

  private Logger LOGGER = LoggerFactory.getLogger(OfferServiceImpl.class);
  private final RestClient offerRestClient;
  private final OfferRepository offerRepository;
  private final ExRateService exRateService;


  @Value("${offers.api.baseUrl}")

  private String restServiceUrl;

  public OfferServiceImpl(
          @Qualifier("offersRestClient") RestClient offerRestClient,
          OfferRepository offerRepository,
          ExRateService exRateService) {
    this.offerRestClient = offerRestClient;
    this.offerRepository = offerRepository;
    this.exRateService = exRateService;

  }

  @Override
  public void createOffer(AddOfferDTO addOfferDTO) {
    LOGGER.info("Creating new offer...");

    offerRestClient
        .post()
        .uri("/offers")
        .body(addOfferDTO)
        .retrieve();
  }

  @Override
  public void deleteOffer(Long offerId) {
    LOGGER.info("Deleted id");

      offerRestClient
              .delete()
              .uri("/offers/{id}", offerId)
              .retrieve()
              .toBodilessEntity()
              .getStatusCode();
  }

  @Override
  public OfferDetailsDTO getOfferDetails(Long id) {

    return offerRestClient
        .get()
        .uri("/offers/{id}", id)
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .body(OfferDetailsDTO.class);
  }
  @Override
  public List<OfferSummaryDTO> getAllOffersSummary() {
    LOGGER.info("Get all offers...");

    return offerRestClient
        .get()
        .uri("/offers")
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
            .body(new ParameterizedTypeReference<>(){});
  }

}
