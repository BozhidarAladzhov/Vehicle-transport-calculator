package com.example.vehicle_transport_calculator.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class LoginController {



    @GetMapping("/login")
    public String login() {
        return "auth-login";
    }



}
