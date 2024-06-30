package com.example.vehicle_transport_calculator.model.entity;

import com.example.vehicle_transport_calculator.model.entity.enums.PortOfDischarge;
import com.example.vehicle_transport_calculator.model.entity.enums.PortOfLoading;
import com.example.vehicle_transport_calculator.model.entity.enums.VehicleEngine;
import jakarta.persistence.*;

@Entity
@Table(name = "quotes")
public class Quote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private User quoteBy;

    @OneToOne
    private Vehicle quoteFor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private PortOfLoading portOfLoading;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private PortOfDischarge portOfDischarge;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private VehicleEngine vehicleEngine;

    public Quote() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getQuoteBy() {
        return quoteBy;
    }

    public void setQuoteBy(User quoteBy) {
        this.quoteBy = quoteBy;
    }

    public Vehicle getQuoteFor() {
        return quoteFor;
    }

    public void setQuoteFor(Vehicle quoteFor) {
        this.quoteFor = quoteFor;
    }

    public PortOfLoading getPortOfLoading() {
        return portOfLoading;
    }

    public void setPortOfLoading(PortOfLoading portOfLoading) {
        this.portOfLoading = portOfLoading;
    }

    public PortOfDischarge getPortOfDischarge() {
        return portOfDischarge;
    }

    public void setPortOfDischarge(PortOfDischarge portOfDischarge) {
        this.portOfDischarge = portOfDischarge;
    }

    public VehicleEngine getVehicleEngine() {
        return vehicleEngine;
    }

    public void setVehicleEngine(VehicleEngine vehicleEngine) {
        this.vehicleEngine = vehicleEngine;
    }


}
