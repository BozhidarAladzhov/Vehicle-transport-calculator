package com.example.vehicle_transport_calculator.service.impl;


import com.example.vehicle_transport_calculator.model.dto.UserRegistrationDTO;
import com.example.vehicle_transport_calculator.model.entity.UserEntity;
import com.example.vehicle_transport_calculator.repo.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

  private UserServiceImpl toTest;

  @Captor
  private ArgumentCaptor<UserEntity> userEntityCaptor;

  @Mock
  private UserRepository mockUserRepository;

  @Mock
  private PasswordEncoder mockPasswordEncoder;

  @BeforeEach
  void setUp() {

    toTest = new UserServiceImpl(
        new ModelMapper(),
        mockPasswordEncoder,
        mockUserRepository
    );

  }

  @Test
  void testUserRegistration() {
    // Arrange

    UserRegistrationDTO userRegistrationDTO =
        new UserRegistrationDTO()
            .setFirstName("Gergana")
            .setLastName("Aladjova")
            .setPassword("testpassword")
            .setEmail("test@mailtest.com");

    when(mockPasswordEncoder.encode(userRegistrationDTO.getPassword()))
        .thenReturn(userRegistrationDTO.getPassword()+userRegistrationDTO.getPassword());


    // ACT
    toTest.registerUser(userRegistrationDTO);

    // Assert
    verify(mockUserRepository).save(userEntityCaptor.capture());

    UserEntity actualSavedEntity = userEntityCaptor.getValue();

    Assertions.assertNotNull(actualSavedEntity);
    Assertions.assertEquals(userRegistrationDTO.getFirstName(), actualSavedEntity.getFirstName());
    Assertions.assertEquals(userRegistrationDTO.getLastName(), actualSavedEntity.getLastName());
    Assertions.assertEquals(userRegistrationDTO.getPassword() + userRegistrationDTO.getPassword(),
        actualSavedEntity.getPassword());
    Assertions.assertEquals(userRegistrationDTO.getEmail(), actualSavedEntity.getEmail());
  }

}
