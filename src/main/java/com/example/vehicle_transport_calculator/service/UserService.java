package com.example.vehicle_transport_calculator.service;


import com.example.vehicle_transport_calculator.model.dto.UserRegistrationDTO;
import com.example.vehicle_transport_calculator.model.user.VtcUserDetails;
import java.util.Optional;


public interface UserService {

    void registerUser(UserRegistrationDTO userRegistration);

    boolean isUniqueEmail(String email);

    Optional<VtcUserDetails> getCurrentUser();

}
