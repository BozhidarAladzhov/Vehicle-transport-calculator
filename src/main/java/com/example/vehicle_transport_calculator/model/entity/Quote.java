package com.example.vehicle_transport_calculator.model.entity;

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

    @OneToOne
    private TransportRoute quoteRoute;

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

    public TransportRoute getQuoteRoute() {
        return quoteRoute;
    }

    public void setQuoteRoute(TransportRoute quoteRoute) {
        this.quoteRoute = quoteRoute;
    }
}
