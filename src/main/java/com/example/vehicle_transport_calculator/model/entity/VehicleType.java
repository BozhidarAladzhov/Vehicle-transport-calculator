package com.example.vehicle_transport_calculator.model.entity;

import com.example.vehicle_transport_calculator.model.entity.enums.VehicleTypeEnum;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "vehicle_types")
public class VehicleType {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private VehicleTypeEnum vehicleType;

    @Column(nullable = false)
    private String description;

    @OneToMany(mappedBy = "vehicleType")
    private Set<Vehicle> vehicles;

    public VehicleType() {

        this.vehicles = new HashSet<>();
    }

    public VehicleType(VehicleTypeEnum vehicleType, String description) {
        super();
        this.vehicleType = vehicleType;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public VehicleTypeEnum getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleTypeEnum vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(Set<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }
}
