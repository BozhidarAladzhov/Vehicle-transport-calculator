package com.example.vehicle_transport_calculator.service;

import java.util.Map;

public interface JwtService {
  String generateToken(String userId, Map<String, Object> claims);
}
