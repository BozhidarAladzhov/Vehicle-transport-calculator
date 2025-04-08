package com.example.vehicle_transport_calculator.web;

import com.example.vehicle_transport_calculator.model.entity.UserEntity;
import com.example.vehicle_transport_calculator.service.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {


    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/dashboard")
    public String showAdminDashboard(Model model) {
        List<UserEntity> users = adminService.getAllUsers();
        model.addAttribute("users", users);
        return "admin/dashboard"; // Returns the HTML view for the admin dashboard
    }


    @PostMapping("/delete/{userId}")
    public String deleteUser(@PathVariable Long userId) {
        adminService.deleteUser(userId);
        return "redirect:/admin/dashboard"; // Redirect to the admin dashboard after deletion
    }


}
