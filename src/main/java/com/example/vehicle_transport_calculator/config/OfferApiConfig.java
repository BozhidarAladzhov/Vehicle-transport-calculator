package com.example.vehicle_transport_calculator.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "offers.api")
public class OfferApiConfig {
  private String baseUrl;

  public String getBaseUrl() {
    return baseUrl;
  }

  public OfferApiConfig setBaseUrl(String baseUrl) {
    this.baseUrl = baseUrl;
    return this;
  }
}
