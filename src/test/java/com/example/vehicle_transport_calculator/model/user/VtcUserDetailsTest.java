package com.example.vehicle_transport_calculator.model.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.UUID;

public class VtcUserDetailsTest {

    @Test
    public void testConstructorAndGetters() {
        UUID uuid = UUID.randomUUID();
        String username = "bojidar@test.com";
        String password = "test";
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));

        VtcUserDetails userDetails = new VtcUserDetails(
                uuid,
                username,
                password,
                authorities,
                "bojidar",
                "aladjov"
        );

        Assertions.assertEquals(uuid, userDetails.getUuid());
        Assertions.assertEquals("bojidar", userDetails.getFirstName());
        Assertions.assertEquals("aladjov", userDetails.getLastName());
        Assertions.assertEquals("bojidar@test.com", userDetails.getUsername());
        Assertions.assertEquals("test", userDetails.getPassword());
        Assertions.assertTrue(userDetails.getAuthorities().containsAll(authorities));
        Assertions.assertEquals(authorities.size(), userDetails.getAuthorities().size());
    }

    @Test
    public void testGetFullNameBothPresent() {
        VtcUserDetails user = new VtcUserDetails(
                UUID.randomUUID(),
                "test",
                "pass",
                List.of(),
                "Bojidar",
                "Aladjov"
        );
        Assertions.assertEquals("Bojidar Aladjov", user.getFullName());
    }

    @Test
    public void testGetFullNameFirstOnly() {
        VtcUserDetails user = new VtcUserDetails(
                UUID.randomUUID(),
                "test",
                "pass",
                List.of(),
                "Bojidar",
                null
        );
        Assertions.assertEquals("Bojidar", user.getFullName());
    }

    @Test
    public void testGetFullNameLastOnly() {
        VtcUserDetails user = new VtcUserDetails(
                UUID.randomUUID(),
                "test",
                "pass",
                List.of(),
                null,
                "Aladjov"
        );
        Assertions.assertEquals("Aladjov", user.getFullName());
    }

    @Test
    public void testGetFullNameNone() {
        VtcUserDetails user = new VtcUserDetails(
                UUID.randomUUID(),
                "test",
                "pass",
                List.of(),
                null,
                null
        );
        Assertions.assertEquals("", user.getFullName());
    }

}
