package com.example.vehicle_transport_calculator.repo;

import com.example.vehicle_transport_calculator.model.entity.QuoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuoteRepository extends JpaRepository<QuoteEntity, Long> {
}
