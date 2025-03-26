package com.example.vehicle_transport_calculator.repo;

import com.example.vehicle_transport_calculator.model.entity.ExRateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExRateRepository extends JpaRepository<ExRateEntity, Long> {
    Optional<ExRateEntity> findByCurrency(String currency);
}
