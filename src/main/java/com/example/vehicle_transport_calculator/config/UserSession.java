package com.example.vehicle_transport_calculator.config;

import com.example.vehicle_transport_calculator.model.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class UserSession {

    private long id;
    private String email;

    public void login(User user){
        this.id = user.getId();
        this.email = user.getEmail();

    }

    public boolean isUserLoggedIn (){
        return id != 0;
    }


    public void logout() {
        id = 0;
        email = "";
    }

}
