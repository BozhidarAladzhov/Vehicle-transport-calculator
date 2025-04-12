package com.example.vehicle_transport_calculator.service.impl;


import com.example.vehicle_transport_calculator.model.entity.UserEntity;
import com.example.vehicle_transport_calculator.model.entity.UserRoleEntity;
import com.example.vehicle_transport_calculator.model.enums.UserRoleEnum;
import com.example.vehicle_transport_calculator.model.user.VtcUserDetails;
import com.example.vehicle_transport_calculator.repo.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;

class VtcUserDetailsServiceTest {

  @Mock
  private UserRepository userRepository;

  private VtcUserDetailsService vtcUserDetailsService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    vtcUserDetailsService = new VtcUserDetailsService(userRepository);
  }

  @Test
  public void testLoadUserByUsernameSuccess() {
    // Prepare mock data
    String email = "bojidar@test.com";
    UserEntity userEntity = new UserEntity();
    userEntity.setEmail(email);
    userEntity.setPassword("test");
    userEntity.setUuid(UUID.randomUUID());
    userEntity.setFirstName("Bojidar");
    userEntity.setLastName("Aladjov");
    userEntity.setRoles(List.of(new UserRoleEntity().setRole(UserRoleEnum.USER)));

    when(userRepository.findByEmail(email)).thenReturn(Optional.of(userEntity));

    // Call the method
    VtcUserDetails userDetails = (VtcUserDetails) vtcUserDetailsService.loadUserByUsername(email);

    // Assertions
    Assertions.assertNotNull(userDetails);
    Assertions.assertEquals(email, userDetails.getUsername());
    Assertions.assertEquals("test", userDetails.getPassword());
    Assertions.assertEquals("Bojidar", userDetails.getFirstName());
    Assertions.assertEquals("Aladjov", userDetails.getLastName());
    Assertions.assertEquals(1, userDetails.getAuthorities().size());
    Assertions.assertTrue(userDetails.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_USER")));
  }

  @Test
  public void testLoadUserByUsernameUserNotFound() {
    // Prepare mock data
    String email = "bojidar@dosntexist.com";

    when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

    // Call the method and assert exception is thrown
    Assertions.assertThrows(UsernameNotFoundException.class, () -> vtcUserDetailsService.loadUserByUsername(email));
  }

  @Test
  public void testMapUserEntityToVtcUserDetails() {
    // Prepare mock data
    UserEntity userEntity = new UserEntity();
    userEntity.setEmail("bojidar@test.com");
    userEntity.setPassword("test");
    userEntity.setUuid(UUID.randomUUID());
    userEntity.setFirstName("Bojidar");
    userEntity.setLastName("Aladjov");
    userEntity.setRoles(List.of(new UserRoleEntity().setRole(UserRoleEnum.USER)));

    // Call the static map method directly
    VtcUserDetails userDetails = (VtcUserDetails) VtcUserDetailsService.map(userEntity);

    // Assertions
    Assertions.assertNotNull(userDetails);
    Assertions.assertEquals("bojidar@test.com", userDetails.getUsername());
    Assertions.assertEquals("test", userDetails.getPassword());
    Assertions.assertEquals("Bojidar", userDetails.getFirstName());
    Assertions.assertEquals("Aladjov", userDetails.getLastName());
    Assertions.assertEquals(1, userDetails.getAuthorities().size());
    Assertions.assertTrue(userDetails.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_USER")));
  }

  @Test
  public void testMapRoleToGrantedAuthority() {
    // Prepare mock data
    UserRoleEnum role = UserRoleEnum.USER;

    // Call the static map method directly
    GrantedAuthority authority = VtcUserDetailsService.map(role);

    // Assertions
    Assertions.assertNotNull(authority);
    Assertions.assertEquals("ROLE_USER", authority.getAuthority());
  }

}
