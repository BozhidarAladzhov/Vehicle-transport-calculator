package com.example.vehicle_transport_calculator.service.impl;


import com.example.vehicle_transport_calculator.service.JwtService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class JwtServiceImpl implements JwtService {

  private final String jwtSecret;
  private final long expiration;

  public JwtServiceImpl(@Value("${jwt.secret}") String jwtSecret,
                        @Value("${jwt.expiration}") long expiration) {
    this.jwtSecret = jwtSecret;
    this.expiration = expiration;
  }

  @Override
  public String generateToken(String userId, Map<String, Object> claims) {
    var now = new Date();

    return Jwts
        .builder()
        .setClaims(claims)
        .setSubject(userId)
        .setIssuedAt(now)
        .setNotBefore(now)
        .setExpiration(new Date(now.getTime() + expiration))
        .signWith(getSigningKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  private Key getSigningKey() {
    byte[] keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
