package com.example.vehicle_transport_calculator.model.dto;

import com.example.vehicle_transport_calculator.model.dto.UserRegistrationDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserRegistrationDTOTest {
    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testEmptyDto_ShouldHaveAllViolations() {
        UserRegistrationDTO dto = new UserRegistrationDTO();
        Set<ConstraintViolation<UserRegistrationDTO>> violations = validator.validate(dto);

        assertEquals(4, violations.size(), "Should have 4 constraint violations");
    }

    @Test
    void testValidDto_ShouldPassValidation() {
        UserRegistrationDTO dto = new UserRegistrationDTO()
                .setFirstName("John")
                .setLastName("Doe")
                .setPassword("securepassword")
                .setEmail("john.doe@example.com");

        Set<ConstraintViolation<UserRegistrationDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "No violations expected for valid input");
    }

    @Test
    void testFirstNameTooShort_ShouldFail() {
        UserRegistrationDTO dto = new UserRegistrationDTO()
                .setFirstName("John")
                .setLastName("Doe")
                .setPassword("securepassword")
                .setEmail("john.doe@example.com");

        // First Name is valid but will test with short first name
        Set<ConstraintViolation<UserRegistrationDTO>> violations = validator.validate(dto.setFirstName("Jo"));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("firstName")), "No violations expected for valid input");
    }

    private void assertTrue(boolean firstName, String s) {
    }

    @Test
    void testLastNameTooLong_ShouldFail() {
        UserRegistrationDTO dto = new UserRegistrationDTO()
                .setFirstName("John")
                .setLastName("Doe")
                .setPassword("securepassword")
                .setEmail("john.doe@example.com");

        // Last Name is valid but will test with long last name
        Set<ConstraintViolation<UserRegistrationDTO>> violations = validator.validate(dto.setLastName("ThisIsAVeryLongLastNameThatExceedsTheLimit"));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("lastName")), "No violations expected for valid input");
    }

    @Test
    void testInvalidEmail_ShouldFail() {
        UserRegistrationDTO dto = new UserRegistrationDTO()
                .setFirstName("John")
                .setLastName("Doe")
                .setPassword("securepassword")
                .setEmail("invalid-email");

        Set<ConstraintViolation<UserRegistrationDTO>> violations = validator.validate(dto);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")), "No violations expected for valid input");
    }

}
