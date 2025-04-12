package com.example.vehicle_transport_calculator.model.dto;

import com.example.vehicle_transport_calculator.model.dto.UserRegistrationDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class UserRegistrationDTOTest {
    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidUserRegistrationDTO() {
        UserRegistrationDTO dto = new UserRegistrationDTO()
                .setFirstName("Bojidar")
                .setLastName("Aladjov")
                .setPassword("test")
                .setEmail("bojidar@test.com");

        Set<ConstraintViolation<UserRegistrationDTO>> violations = validator.validate(dto);
        Assertions.assertTrue(violations.isEmpty());

        // Test getters
        assertEquals("Bojidar", dto.getFirstName());
        assertEquals("Aladjov", dto.getLastName());
        assertEquals("test", dto.getPassword());
        assertEquals("bojidar@test.com", dto.getEmail());

        // toString test
        String output = dto.toString();
        Assertions.assertTrue(output.contains("Bojidar"));
        Assertions.assertTrue(output.contains("Aladjov"));
        Assertions.assertTrue(output.contains("[PROVIDED]"));
        Assertions.assertTrue(output.contains("bojidar@test.com"));
    }

    @Test
    public void testInvalidUserRegistrationDTO() {
        UserRegistrationDTO dto = new UserRegistrationDTO()
                .setFirstName("B") // too short
                .setLastName("")    // empty
                .setPassword("")    // empty
                .setEmail("bademail"); // invalid

        Set<ConstraintViolation<UserRegistrationDTO>> violations = validator.validate(dto);

        // Should have violations
        Assertions.assertFalse(violations.isEmpty());

        // Optional: check the number of violations or specific ones
        Assertions.assertEquals(5, violations.size());
    }

    @Test
    public void testToStringWhenPasswordIsNull() {
        UserRegistrationDTO dto = new UserRegistrationDTO()
                .setFirstName("Bojidar")
                .setLastName("Aladjov")
                .setEmail("bojidar@test.com");
        // No password set

        String output = dto.toString();
        Assertions.assertTrue(output.contains("N/A"));
    }

}
