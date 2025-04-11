package com.example.vehicle_transport_calculator;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;

@SpringBootTest
class VehicleTransportCalculatorApplicationTests {

	@MockBean
	private DataSourceInitializer dataSourceInitializer;

	@Test
	void contextLoads() {
	}

}
