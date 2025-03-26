package com.example.vehicle_transport_calculator.controller;

import com.example.vehicle_transport_calculator.model.dto.UserRegistrationDTO;
import com.example.vehicle_transport_calculator.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("registerData")
    public UserRegistrationDTO createEmptyDTO() {
        return new UserRegistrationDTO();
    }

    @GetMapping("/register")
    public String viewRegister() {
        return "auth-register";
    }


    @PostMapping("/register")
    public String register(UserRegistrationDTO registerDTO) {

        userService.registerUser(registerDTO);


        return "redirect:/";
    }

}
