package com.example.vehicle_transport_calculator.service.impl;


import com.example.vehicle_transport_calculator.config.ForexApiConfig;
import com.example.vehicle_transport_calculator.model.entity.ExRateEntity;
import com.example.vehicle_transport_calculator.repo.ExRateRepository;
import com.example.vehicle_transport_calculator.service.exception.ApiObjectNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.Mock.Strictness;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExRateServiceImplTest {

  private static final class TestRates {
    // TUD -> base -> Test dollar
    // CUR1 -> 2
    // CUR2 -> 0.5

    private static final String BASE_CURRENCY = "TUD";

    private static final ExRateEntity CUR1 = new ExRateEntity()
        .setCurrency("CUR1").setRate(new BigDecimal("2"));

    private static final ExRateEntity CUR2 = new ExRateEntity()
        .setCurrency("CUR2").setRate(new BigDecimal("0.5"));
  }

  private ExRateServiceImpl toTest;

  @Mock(strictness = Strictness.LENIENT)
  private ExRateRepository mockRepository;

  @BeforeEach
  void setUp() {
    toTest = new ExRateServiceImpl(
        mockRepository,
        null,
        new ForexApiConfig().setBase(TestRates.BASE_CURRENCY)
    );
  }

  // 1 SUD ->   2 CUR1
  // 1 SUD -> 0.5 CUR2

  @ParameterizedTest(name = "Converting {2} {0} to {1}. Expected {3}")
  @CsvSource(
      textBlock = """
          TUD, CUR1, 1, 2.00
          TUD, CUR1, 2, 4.00
          TUD, TUD,  1, 1
          CUR1,CUR2, 1, 0.25
          CUR2,CUR1, 1, 4.00
          BUD, BUD, 1, 1
          """
  )
  void testConvert(String from,
      String to,
      BigDecimal amount,
      BigDecimal expected) {

    initExRates();

    BigDecimal result = toTest.convert(from, to, amount);
    Assertions.assertEquals(expected, result);
  }

  @Test
  void testApiObjectNotFoundException() {
    Assertions.assertThrows(ApiObjectNotFoundException.class,
        () -> toTest.convert("NO_EXIST_1", "NOT_EXIST_2", BigDecimal.ONE)
    );
  }

  private void initExRates() {
    when(mockRepository.findByCurrency(TestRates.CUR1.getCurrency()))
        .thenReturn(Optional.of(TestRates.CUR1));
    when(mockRepository.findByCurrency(TestRates.CUR2.getCurrency()))
        .thenReturn(Optional.of(TestRates.CUR2));
  }

  @Test
  void testHasInitializedExRates() {
    when(mockRepository.count()).thenReturn(0L);
    Assertions.assertFalse(toTest.hasInitializedExRates());

    when(mockRepository.count()).thenReturn(-5L);
    Assertions.assertFalse(toTest.hasInitializedExRates());

    when(mockRepository.count()).thenReturn(6L);
    Assertions.assertTrue(toTest.hasInitializedExRates());
  }
}
