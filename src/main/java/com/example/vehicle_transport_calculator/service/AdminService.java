package com.example.vehicle_transport_calculator.service;

import com.example.vehicle_transport_calculator.model.entity.UserEntity;
import com.example.vehicle_transport_calculator.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    private final UserRepository userRepository;

    @Autowired
    public AdminService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(Long userId) {
        Optional<UserEntity> user = userRepository.findById(userId);
        if (user.isPresent()) {
            userRepository.delete(user.get());
        }
    }

}
