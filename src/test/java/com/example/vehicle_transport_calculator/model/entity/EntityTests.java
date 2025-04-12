package com.example.vehicle_transport_calculator.model.entity;

import com.example.vehicle_transport_calculator.model.enums.EngineTypeEnum;
import com.example.vehicle_transport_calculator.model.enums.PortOfDischargeEnum;
import com.example.vehicle_transport_calculator.model.enums.PortOfLoadingEnum;
import com.example.vehicle_transport_calculator.model.enums.UserRoleEnum;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class EntityTests {

    private Validator validator;

    @BeforeEach
    public void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testBaseEntity() {
        BaseEntity base = new BaseEntity().setId(100L);
        Assertions.assertEquals(100L, base.getId());
    }

    @Test
    public void testExRateEntityValid() {
        ExRateEntity rate = new ExRateEntity()
                .setCurrency("USD")
                .setRate(BigDecimal.valueOf(1.23));

        Set<ConstraintViolation<ExRateEntity>> violations = validator.validate(rate);
        Assertions.assertTrue(violations.isEmpty());

        Assertions.assertEquals("USD", rate.getCurrency());
        Assertions.assertEquals(BigDecimal.valueOf(1.23), rate.getRate());

        String str = rate.toString();
        Assertions.assertTrue(str.contains("USD"));
    }

    @Test
    public void testExRateEntityInvalid() {
        ExRateEntity rate = new ExRateEntity()
                .setCurrency("") // @NotEmpty fails
                .setRate(BigDecimal.valueOf(-5)); // @Positive fails

        Set<ConstraintViolation<ExRateEntity>> violations = validator.validate(rate);
        Assertions.assertEquals(2, violations.size());
    }

    @Test
    public void testOfferEntityValid() {
        OfferEntity offer = (OfferEntity) new OfferEntity()
                .setDescription("Shipping offer")
                .setPortOfLoading(PortOfLoadingEnum.NEW_YORK)
                .setPortOfDischarge(PortOfDischargeEnum.ROTTERDAM)
                .setEngine(EngineTypeEnum.DIESEL)
                .setPrice(5000)
                .setId(55L);

        Set<ConstraintViolation<OfferEntity>> violations = validator.validate(offer);
        Assertions.assertTrue(violations.isEmpty());

        Assertions.assertEquals("Shipping offer", offer.getDescription());
        Assertions.assertEquals(5000, offer.getPrice());
        Assertions.assertEquals(55L, offer.getId());
    }

    @Test
    public void testOfferEntityInvalid() {
        OfferEntity offer = new OfferEntity()
                .setDescription("") // @NotEmpty fails
                .setPrice(-100); // @Positive fails

        Set<ConstraintViolation<OfferEntity>> violations = validator.validate(offer);
        Assertions.assertEquals(2, violations.size());
    }

    @Test
    public void testUserEntity() {
        UserRoleEntity role = new UserRoleEntity().setId(1L).setRole(UserRoleEnum.ADMIN);
        List<UserRoleEntity> roles = List.of(role);
        UUID uuid = UUID.randomUUID();

        UserEntity user = (UserEntity) new UserEntity()
                .setEmail("bojidar@test.com")
                .setFirstName("Bojidar")
                .setLastName("Aladjov")
                .setPassword("test")
                .setRoles(roles)
                .setUuid(uuid)
                .setId(101L);

        Assertions.assertEquals("bojidar@test.com", user.getEmail());
        Assertions.assertEquals("Bojidar", user.getFirstName());
        Assertions.assertEquals("Aladjov", user.getLastName());
        Assertions.assertEquals("test", user.getPassword());
        Assertions.assertEquals(roles, user.getRoles());
        Assertions.assertEquals(uuid, user.getUuid());
        Assertions.assertEquals(101L, user.getId());

        String toStringOutput = user.toString();
        Assertions.assertTrue(toStringOutput.contains("bojidar@mail.com"));
        Assertions.assertTrue(toStringOutput.contains("roles"));
    }

    @Test
    public void testUserRoleEntityValid() {
        UserRoleEntity role = new UserRoleEntity()
                .setId(2L)
                .setRole(UserRoleEnum.USER);

        Set<ConstraintViolation<UserRoleEntity>> violations = validator.validate(role);
        Assertions.assertTrue(violations.isEmpty());

        Assertions.assertEquals(2L, role.getId());
        Assertions.assertEquals(UserRoleEnum.USER, role.getRole());
    }

    @Test
    public void testUserRoleEntityInvalid() {
        UserRoleEntity role = new UserRoleEntity()
                .setRole(null); // @NotNull fails

        Set<ConstraintViolation<UserRoleEntity>> violations = validator.validate(role);
        Assertions.assertEquals(1, violations.size());
    }

}
