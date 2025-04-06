package com.example.vehicle_transport_calculator.web;

import com.example.vehicle_transport_calculator.model.user.VtcUserDetails;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@SpringBootTest
public class HomeControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "user", roles = {"USER"}) // Mock authenticated user with role USER
    void testHome_WithLoggedInUser() throws Exception {
        // Arrange: Assuming VtcUserDetails is set as principal with a full name
        mockMvc.perform(get("/"))
                .andExpect(view().name("index"))
                .andExpect(model().attribute("welcomeMessage", "Anonymous")); // Adjust based on your UserDetails implementation
    }


    @Test
    void testHome_WithAnonymousUser() throws Exception {
        // Act: Access the home page as an anonymous user
        mockMvc.perform(get("/"))
                .andExpect(view().name("index"))
                .andExpect(model().attribute("welcomeMessage", "Anonymous"));
    }


}
