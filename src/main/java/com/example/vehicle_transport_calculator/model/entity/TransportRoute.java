package com.example.vehicle_transport_calculator.model.entity;

import com.example.vehicle_transport_calculator.model.entity.enums.PortOfDischarge;
import com.example.vehicle_transport_calculator.model.entity.enums.PortOfLoading;
import jakarta.persistence.*;

@Entity
@Table(name = "transport_routes")
public class TransportRoute {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


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

    @OneToOne(mappedBy = "quoteRoute")
    private Quote quoteRoute;

    public TransportRoute() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public int getOceanFreight() {
        return oceanFreight;
    }

    public void setOceanFreight(int oceanFreight) {
        this.oceanFreight = oceanFreight;
    }

    public boolean isHazardousCargo() {
        return hazardousCargo;
    }

    public void setHazardousCargo(boolean hazardousCargo) {
        this.hazardousCargo = hazardousCargo;
    }

    public int getTerminalOperations() {
        return terminalOperations;
    }

    public void setTerminalOperations(int terminalOperations) {
        this.terminalOperations = terminalOperations;
    }

    public Quote getQuoteRoute() {
        return quoteRoute;
    }

    public void setQuoteRoute(Quote quoteRoute) {
        this.quoteRoute = quoteRoute;
    }
}
