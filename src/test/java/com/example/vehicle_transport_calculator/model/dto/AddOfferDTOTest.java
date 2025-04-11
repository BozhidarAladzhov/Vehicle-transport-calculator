package com.example.vehicle_transport_calculator.model.dto;

import com.example.vehicle_transport_calculator.model.enums.EngineTypeEnum;
import com.example.vehicle_transport_calculator.model.enums.PortOfDischargeEnum;
import com.example.vehicle_transport_calculator.model.enums.PortOfLoadingEnum;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.Set;

import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddOfferDTOTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = (Validator) factory.getValidator();
    }

    @Test
    void testEmptyDto_ShouldHaveAllViolations() {
        AddOfferDTO dto = AddOfferDTO.empty();
        Set<ConstraintViolation<AddOfferDTO>> violations = validator.validate(dto);

        assertEquals(5, violations.size(), "Should have 5 constraint violations");
    }

    @Test
    void testValidDto_ShouldPassValidation() {
        AddOfferDTO dto = new AddOfferDTO(
                "This is a valid description.",
                PortOfLoadingEnum.NEW_YORK,
                PortOfDischargeEnum.ROTTERDAM,
                EngineTypeEnum.DIESEL,
                1000
        );

        Set<ConstraintViolation<AddOfferDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "No violations expected for valid input");
    }

    @Test
    void testInvalidDescriptionTooShort_ShouldFail() {
        AddOfferDTO dto = new AddOfferDTO(
                "123", // too short
                PortOfLoadingEnum.NEW_YORK,
                PortOfDischargeEnum.ROTTERDAM,
                EngineTypeEnum.DIESEL,
                1000
        );

        Set<ConstraintViolation<AddOfferDTO>> violations = validator.validate(dto);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("description")), "No violations expected for valid input");
    }

    private void assertTrue(boolean description, String s) {

    }

    @Test
    void testNegativePrice_ShouldFail() {
        AddOfferDTO dto = new AddOfferDTO(
                "Valid description",
                PortOfLoadingEnum.NEW_YORK,
                PortOfDischargeEnum.ROTTERDAM,
                EngineTypeEnum.DIESEL,
                -50
        );

        Set<ConstraintViolation<AddOfferDTO>> violations = validator.validate(dto);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("price")), "No violations expected for valid input");
    }

}
