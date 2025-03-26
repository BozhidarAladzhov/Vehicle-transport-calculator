package com.example.vehicle_transport_calculator.model.user;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.GrantedAuthority;


import java.util.Collection;
import java.util.UUID;


public class VtcUserDetails extends User {

    private final UUID uuid;
    private final String firstName;
    private final String lastName;

    public VtcUserDetails(
            UUID uuid,
            String username,
            String password,
            Collection<? extends GrantedAuthority> authorities,
            String firstName,
            String lastName
    ) {
       super(username, password, authorities);
        this.uuid = uuid;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName () {
        StringBuilder fullName = new StringBuilder();

        if (firstName != null){
            fullName.append(firstName);
        }

        if (lastName != null){
            if (!fullName.isEmpty()){
                fullName.append(" ");
            }
            fullName.append(lastName);
        }

        return  fullName.toString();
    }
}
