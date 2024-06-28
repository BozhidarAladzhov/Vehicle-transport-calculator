package com.example.vehicle_transport_calculator.model.entity;

import com.example.vehicle_transport_calculator.model.entity.enums.PortOfDischarge;
import com.example.vehicle_transport_calculator.model.entity.enums.PortOfLoading;
import com.example.vehicle_transport_calculator.model.entity.enums.VehicleEngine;
import com.example.vehicle_transport_calculator.model.entity.enums.VehicleType;
import jakarta.persistence.*;

@Entity
@Table(name = "vehicles")
public class VehicleQuote {

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

    @Column(nullable = false, unique = true)
    private String vin;

    @Column(nullable = false, unique = true)
    private VehicleEngine vehicleEngine;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PortOfLoading portOfLoading;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PortOfDischarge portOfDischarge;

    @Column(nullable = false)
    private int oceanFreight;

    @Column(nullable = false)
    private boolean hazardousCargo;

    @Column(nullable = false)
    private int terminalOperations;

    @ManyToOne
    private User quoteBy;





}
