package com.example.vehicle_transport_calculator.service;

import com.example.vehicle_transport_calculator.config.UserSession;
import com.example.vehicle_transport_calculator.model.entity.User;
import com.example.vehicle_transport_calculator.model.entity.dto.UserRegisterDto;
import com.example.vehicle_transport_calculator.repo.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserSession userSession;

    public UserService(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, UserSession userSession) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.userSession = userSession;
    }

    public boolean register(UserRegisterDto data) {

        boolean isUsernameOrEmailTaken =
                userRepository.existsByEmail(data.getEmail());

        if (isUsernameOrEmailTaken) {
            return false;
        }

        User mapped = modelMapper.map(data, User.class);
        mapped.setPassword(passwordEncoder.encode(mapped.getPassword()));

        userRepository.save(mapped);

        return true;
    }
}
