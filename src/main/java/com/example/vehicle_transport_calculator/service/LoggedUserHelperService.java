package com.example.vehicle_transport_calculator.service;



import com.example.vehicle_transport_calculator.model.entity.UserEntity;
import com.example.vehicle_transport_calculator.model.enums.UserRoleEnum;
import com.example.vehicle_transport_calculator.repo.UserRepository;
import com.example.vehicle_transport_calculator.service.exception.UserNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class LoggedUserHelperService {
    private final UserRepository userRepository;

    public LoggedUserHelperService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity get() {
        String email = get().getEmail();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with email: " + email + " was not found"));
    }

    public boolean isLogged() {
        return !hasRole(UserRoleEnum.ANONYMOUS);
    }

    public boolean isAdmin() {
        return hasRole(UserRoleEnum.ADMIN);
    }

    public boolean isModerator() {
        return hasRole(UserRoleEnum.USER);
    }

    public boolean isOnlyUser() {
        return getAuthentication().getAuthorities().stream()
                .allMatch(role -> role.getAuthority().equals("ROLE_" + UserRoleEnum.USER));
    }



    public String getUsername() {
        return getUserDetails().getUsername();
    }

    public boolean hasRole(UserRoleEnum userRoles) {
        return getAuthentication().getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_" + userRoles));
    }

    private UserDetails getUserDetails() {
        return (UserDetails) getAuthentication().getPrincipal();
    }

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
