package com.example.vehicle_transport_calculator.model.entity;

import jakarta.persistence.*;

@Entity
@Table
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private VehicleType vehicleType;

    @Column(nullable = false)
    private int year;

    @Column(nullable = false)
    private String make;

    @Column(nullable = false)
    private String model;

    private String vin;



}
