package com.example.vehicle_transport_calculator.service.impl;


import com.example.vehicle_transport_calculator.model.dto.UserRegistrationDTO;
import com.example.vehicle_transport_calculator.model.entity.UserEntity;
import com.example.vehicle_transport_calculator.model.user.VtcUserDetails;
import com.example.vehicle_transport_calculator.repo.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class UserServiceImplTest {

  private ModelMapper modelMapper;
  private PasswordEncoder passwordEncoder;
  private UserRepository userRepository;

  private UserServiceImpl userService;

  @BeforeEach
  public void setUp() {
    modelMapper = mock(ModelMapper.class);
    passwordEncoder = mock(PasswordEncoder.class);
    userRepository = mock(UserRepository.class);

    userService = new UserServiceImpl(modelMapper, passwordEncoder, userRepository);
  }

  @Test
  public void testRegisterUser() {
    UserRegistrationDTO dto = new UserRegistrationDTO();
    dto.setEmail("bojidar@test.com");
    dto.setPassword("test");

    UserEntity userEntity = new UserEntity();
    when(modelMapper.map(dto, UserEntity.class)).thenReturn(userEntity);
    when(passwordEncoder.encode("test")).thenReturn("hashed");
    when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

    userService.registerUser(dto);

    verify(modelMapper).map(dto, UserEntity.class);
    verify(passwordEncoder).encode("test");
    verify(userRepository).save(userEntity);
    Assertions.assertEquals("hashed", userEntity.getPassword());
  }

  @Test
  public void testIsUniqueEmailReturnsTrue() {
    String email = "bojidar@test.com";
    when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

    boolean result = userService.isUniqueEmail(email);

    Assertions.assertTrue(result);
    verify(userRepository).findByEmail(email);
  }

  @Test
  public void testIsUniqueEmailReturnsFalse() {
    String email = "bojidar@taken.com";
    when(userRepository.findByEmail(email)).thenReturn(Optional.of(new UserEntity()));

    boolean result = userService.isUniqueEmail(email);

    Assertions.assertFalse(result);
    verify(userRepository).findByEmail(email);
  }

  @Test
  public void testGetCurrentUserReturnsUser() {
    VtcUserDetails vtcUser = new VtcUserDetails(
            UUID.randomUUID(),
            "bojidaraladjov",
            "test",
            List.of(),
            "Bojidar",
            "Aladjov"
    );

    Authentication authentication = mock(Authentication.class);
    when(authentication.getPrincipal()).thenReturn(vtcUser);

    SecurityContext context = mock(SecurityContext.class);
    when(context.getAuthentication()).thenReturn(authentication);

    SecurityContextHolder.setContext(context);

    Optional<VtcUserDetails> result = userService.getCurrentUser();

    Assertions.assertTrue(result.isPresent());
    Assertions.assertEquals(vtcUser, result.get());
  }

  @Test
  public void testGetCurrentUserReturnsEmpty() {
    Authentication authentication = mock(Authentication.class);
    when(authentication.getPrincipal()).thenReturn("anonymousUser");

    SecurityContext context = mock(SecurityContext.class);
    when(context.getAuthentication()).thenReturn(authentication);

    SecurityContextHolder.setContext(context);

    Optional<VtcUserDetails> result = userService.getCurrentUser();

    Assertions.assertTrue(result.isEmpty());
  }


}
