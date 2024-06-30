package com.example.vehicle_transport_calculator.controller;

import com.example.vehicle_transport_calculator.model.entity.dto.UserRegisterDto;
import com.example.vehicle_transport_calculator.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserRegisterController {

    private final UserService userService;

    public UserRegisterController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("registerData")
    public UserRegisterDto createEmptyDTO() {
        return new UserRegisterDto();
    }

    @GetMapping("/users/register")
    public String viewRegister() {
        return "auth-register";
    }

    @PostMapping("/users/register")
    public String doRegister(
            @Valid UserRegisterDto data,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors() || !userService.register(data)) {
            redirectAttributes.addFlashAttribute("registerData", data);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.registerData", bindingResult);

            return "redirect:/users/register";
        }


        return "redirect:/users/login";
    }

}
