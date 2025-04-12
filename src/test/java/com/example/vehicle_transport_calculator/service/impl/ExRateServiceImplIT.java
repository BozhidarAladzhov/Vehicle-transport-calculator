package com.example.vehicle_transport_calculator.service.impl;


import com.example.vehicle_transport_calculator.config.ForexApiConfig;
import com.example.vehicle_transport_calculator.model.dto.ExRatesDTO;
import com.example.vehicle_transport_calculator.model.entity.ExRateEntity;
import com.example.vehicle_transport_calculator.repo.ExRateRepository;
import com.example.vehicle_transport_calculator.service.ExRateService;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.maciejwalkowiak.wiremock.spring.ConfigureWireMock;
import com.maciejwalkowiak.wiremock.spring.EnableWireMock;
import com.maciejwalkowiak.wiremock.spring.InjectWireMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.client.RestTemplate;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;

@SpringBootTest
@EnableWireMock(
    @ConfigureWireMock(name = "exchange-rate-service")
)
@WithMockUser(
        username = "bojidar@mail.com",
        roles = {"USER", "ADMIN"}
)
public class ExRateServiceImplIT {

  @InjectWireMock("exchange-rate-service")
  private WireMockServer wireMockServer;

  @MockBean
  private DataSourceInitializer dataSourceInitializer;

  @Autowired
  private ExRateService exRateService;

  @Autowired
  private ExRateRepository exRateRepository;

  @MockBean
  private RestTemplate restTemplate;

  @Autowired
  private ForexApiConfig forexApiConfig;

  @BeforeEach
  void setUp() {
    exRateRepository.deleteAll();
    forexApiConfig.setUrl(wireMockServer.baseUrl() + "/test-currencies");
  }

  @Test
  void testFetchExchangeRates() {
    wireMockServer.stubFor(get("/test-currencies").willReturn(
        aResponse()
            .withHeader("Content-Type", "application/json")
            .withBody(
                """
                  {
                    "base": "USD",
                    "rates" : {
                      "BGN": 1.86,
                      "EUR": 0.91
                    }
                  }
                """
            )
        )
    );

    ExRatesDTO exRatesDTO = exRateService.fetchExRates();

    Assertions.assertEquals("USD", exRatesDTO.base());
    Assertions.assertEquals(2, exRatesDTO.rates().size());
    Assertions.assertEquals(new BigDecimal("1.86"), exRatesDTO.rates().get("BGN"));
    Assertions.assertEquals(new BigDecimal("0.91"), exRatesDTO.rates().get("EUR"));
  }

  @Test
  public void testAllSupportedCurrencies() {
    ExRateEntity usd = new ExRateEntity().setCurrency("USD").setRate(BigDecimal.valueOf(1.0));
    ExRateEntity eur = new ExRateEntity().setCurrency("EUR").setRate(BigDecimal.valueOf(0.85));
    exRateRepository.saveAll(Arrays.asList(usd, eur));

    List<String> currencies = exRateService.allSupportedCurrencies();
    Assertions.assertEquals(2, currencies.size());
    Assertions.assertTrue(currencies.contains("USD"));
    Assertions.assertTrue(currencies.contains("EUR"));
  }

  @Test
  public void testHasInitializedExRates() {
    Assertions.assertFalse(exRateService.hasInitializedExRates());

    ExRateEntity usd = new ExRateEntity().setCurrency("USD").setRate(BigDecimal.valueOf(1.0));
    exRateRepository.save(usd);

    Assertions.assertTrue(exRateService.hasInitializedExRates());
  }

  @Test
  public void testUpdateRates() {
    Map<String, BigDecimal> rates = new HashMap<>();
    rates.put("EUR", BigDecimal.valueOf(0.85));
    rates.put("GBP", BigDecimal.valueOf(0.75));
    ExRatesDTO exRatesDTO = new ExRatesDTO("USD", rates);

    exRateService.updateRates(exRatesDTO);

    List<ExRateEntity> entities = exRateRepository.findAll();
    Assertions.assertEquals(2, entities.size());
    Assertions.assertTrue(entities.stream().anyMatch(e -> e.getCurrency().equals("EUR") && e.getRate().equals(BigDecimal.valueOf(0.85))));
    Assertions.assertTrue(entities.stream().anyMatch(e -> e.getCurrency().equals("GBP") && e.getRate().equals(BigDecimal.valueOf(0.75))));
  }

  @Test
  public void testConvert() {
    ExRateEntity eur = new ExRateEntity().setCurrency("EUR").setRate(BigDecimal.valueOf(0.85));
    ExRateEntity gbp = new ExRateEntity().setCurrency("GBP").setRate(BigDecimal.valueOf(0.75));
    exRateRepository.saveAll(Arrays.asList(eur, gbp));

    BigDecimal amount = BigDecimal.valueOf(100);
    BigDecimal converted = exRateService.convert("EUR", "GBP", amount);
    BigDecimal expectedRate = BigDecimal.valueOf(0.75).divide(BigDecimal.valueOf(0.85), 2, RoundingMode.HALF_DOWN);
    BigDecimal expectedAmount = expectedRate.multiply(amount);

    Assertions.assertEquals(0, expectedAmount.compareTo(converted));
  }



}
