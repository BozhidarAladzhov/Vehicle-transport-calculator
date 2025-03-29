package com.example.vehicle_transport_calculator.service.impl;


import com.example.vehicle_transport_calculator.model.entity.UserEntity;
import com.example.vehicle_transport_calculator.model.entity.UserRoleEntity;
import com.example.vehicle_transport_calculator.model.enums.UserRoleEnum;
import com.example.vehicle_transport_calculator.model.user.VtcUserDetails;
import com.example.vehicle_transport_calculator.repo.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VtcUserDetailsServiceTest {

  private static final String TEST_EMAIL = "user@example.com";
  private static final String NOT_EXISTENT_EMAIL = "noone@example.com";

  private VtcUserDetailsService toTest;
  @Mock
  private UserRepository mockUserRepository;

  @BeforeEach
  void setUp() {
    toTest = new VtcUserDetailsService(mockUserRepository);
  }

  @Test
  void testLoadUserByUsername_UserFound() {

    // Arrange
    UserEntity testUser = new UserEntity()
        .setEmail(TEST_EMAIL)
        .setPassword("passwordtest")
        .setFirstName("Bojidartest")
        .setLastName("Aladjovtest")
        .setRoles(List.of(
            new UserRoleEntity().setRole(UserRoleEnum.ADMIN),
            new UserRoleEntity().setRole(UserRoleEnum.USER)
        ));

    when(mockUserRepository.findByEmail(TEST_EMAIL))
        .thenReturn(Optional.of(testUser));

    // Act
    UserDetails userDetails = toTest.loadUserByUsername(TEST_EMAIL);

    // Assert
    Assertions.assertInstanceOf(VtcUserDetails.class, userDetails);

    VtcUserDetails vtcUserDetails = (VtcUserDetails) userDetails;

    Assertions.assertEquals(TEST_EMAIL, userDetails.getUsername());
    Assertions.assertEquals(testUser.getPassword(), userDetails.getPassword());
    Assertions.assertEquals(testUser.getFirstName(), vtcUserDetails.getFirstName());
    Assertions.assertEquals(testUser.getLastName(), vtcUserDetails.getLastName());
    Assertions.assertEquals(testUser.getFirstName() + " " + testUser.getLastName(),
        vtcUserDetails.getFullName());

    var expectedRoles = testUser.getRoles().stream().map(UserRoleEntity::getRole).map(r -> "ROLE_" + r).toList();
    var actualRoles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

    Assertions.assertEquals(expectedRoles, actualRoles);
  }

  @Test
  void testLoadUserByUsername_UserNotFound() {
    Assertions.assertThrows(
        UsernameNotFoundException.class,
        () -> toTest.loadUserByUsername(NOT_EXISTENT_EMAIL)
    );
  }

}
