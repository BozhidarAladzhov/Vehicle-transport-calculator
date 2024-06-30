package com.example.vehicle_transport_calculator.model.entity;

import com.example.vehicle_transport_calculator.model.entity.enums.VehicleEngine;
import com.example.vehicle_transport_calculator.model.entity.enums.VehicleTypeEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "vehicles")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
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

    @OneToOne(mappedBy = "quoteFor")
    private Quote vehicleQuote;

    public Vehicle() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public VehicleEngine getVehicleEngine() {
        return vehicleEngine;
    }

    public void setVehicleEngine(VehicleEngine vehicleEngine) {
        this.vehicleEngine = vehicleEngine;
    }

    public Quote getVehicleQuote() {
        return vehicleQuote;
    }

    public void setVehicleQuote(Quote vehicleQuote) {
        this.vehicleQuote = vehicleQuote;
    }
}
