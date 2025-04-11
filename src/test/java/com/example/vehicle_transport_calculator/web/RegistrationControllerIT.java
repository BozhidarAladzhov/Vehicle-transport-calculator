package com.example.vehicle_transport_calculator.web;


import com.example.vehicle_transport_calculator.model.entity.UserEntity;
import com.example.vehicle_transport_calculator.repo.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RegistrationControllerIT {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private DataSourceInitializer dataSourceInitializer;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Test
  void testRegistration() throws Exception {

    mockMvc.perform(post("/users/register")
        .param("email", "bojidar@test.com")
        .param("firstName", "Bojidar")
        .param("lastName", "Aladjov")
        .param("password", "testpassword")
            .with(csrf())
    ).andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/users/login"));

    Optional<UserEntity> userEntityOpt = userRepository.findByEmail("bojidar@test.com");

    Assertions.assertTrue(userEntityOpt.isPresent());

    UserEntity userEntity = userEntityOpt.get();

    Assertions.assertEquals("Bojidar", userEntity.getFirstName());
    Assertions.assertEquals("Aladjov", userEntity.getLastName());

    Assertions.assertTrue(passwordEncoder.matches("testpassword", userEntity.getPassword()));
  }
}
