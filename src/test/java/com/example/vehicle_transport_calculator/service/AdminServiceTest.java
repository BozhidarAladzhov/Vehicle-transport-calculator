package com.example.vehicle_transport_calculator.service;

import com.example.vehicle_transport_calculator.model.entity.UserEntity;
import com.example.vehicle_transport_calculator.repo.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AdminService adminService;

    private UserEntity userEntity;

    @BeforeEach
    public void setUp() {
        userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setEmail("bojidar@test.com");
        userEntity.setFirstName("Bojidar");
        userEntity.setLastName("Aladjov");
    }

    @Test
    public void testGetAllUsers() {
        // Arrange
        List<UserEntity> users = List.of(userEntity);
        when(userRepository.findAll()).thenReturn(users);

        // Act
        List<UserEntity> result = adminService.getAllUsers();

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("bojidar@test.com", result.get(0).getEmail());
        Mockito.verify(userRepository, atLeastOnce()).findAll();
    }

    @Test
    public void testDeleteUser_UserExists() {
        // Arrange
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(userEntity));

        // Act
        adminService.deleteUser(1L);

        // Assert
        Mockito.verify(userRepository, atLeastOnce()).findById(1L);
        Mockito.verify(userRepository, atLeastOnce()).delete(userEntity);
    }

    @Test
    public void testDeleteUser_UserNotFound() {
        // Arrange
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        // Act
        adminService.deleteUser(1L);

        // Assert
        Mockito.verify(userRepository, atMostOnce()).findById(1L);
        Mockito.verify(userRepository, never()).delete(any());
    }

}
