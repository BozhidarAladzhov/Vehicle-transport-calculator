package com.example.vehicle_transport_calculator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String notLogged(){
        return "index";
    }

    @GetMapping("/home")
    public String logged (){
        return "home";

    }

}
