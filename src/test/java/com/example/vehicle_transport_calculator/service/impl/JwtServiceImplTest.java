package com.example.vehicle_transport_calculator.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;

import static io.jsonwebtoken.security.Keys.hmacShaKeyFor;

public class JwtServiceImplTest {

    private JwtServiceImpl jwtService;
    private final String jwtSecret = "supersecretkeysupersecretkeysupersecretkey123";
    private final long expiration = 1000 * 60 * 60; // 1 hour

    @BeforeEach
    public void setUp() {
        jwtService = new JwtServiceImpl(jwtSecret, expiration);
    }

    @Test
    public void testGenerateToken_andVerifyClaims() {
        String userId = "user123";
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", "ROLE_USER");
        claims.put("custom", "data");

        String token = jwtService.generateToken(userId, claims);
        Assertions.assertNotNull(token);

        // Decode and verify token
        Claims parsedClaims = Jwts
                .parserBuilder()
                .setSigningKey(jwtSecret.getBytes(StandardCharsets.UTF_8))
                .build()
                .parseClaimsJws(token)
                .getBody();

        Assertions.assertEquals(userId, parsedClaims.getSubject());
        Assertions.assertEquals("ROLE_USER", parsedClaims.get("role"));
        Assertions.assertEquals("data", parsedClaims.get("custom"));
        Assertions.assertNotNull(parsedClaims.getIssuedAt());
        Assertions.assertNotNull(parsedClaims.getExpiration());

        // Check expiration roughly matches
        long diff = parsedClaims.getExpiration().getTime() - parsedClaims.getIssuedAt().getTime();
        Assertions.assertTrue(Math.abs(diff - expiration) < 1000); // ~1 second tolerance
    }

    @Test
    public void testGetSigningKey() {
        // Use reflection to call the private method
        Key key = (Key) ReflectionTestUtils.invokeMethod(jwtService, "getSigningKey");

        Assertions.assertNotNull(key);
        Key expectedKey = hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        Assertions.assertEquals(expectedKey, key);
    }

}
