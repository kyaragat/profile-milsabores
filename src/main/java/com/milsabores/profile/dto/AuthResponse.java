package com.milsabores.profile.dto;

import com.milsabores.profile.model.Role;
import lombok.Data;

@Data
public class AuthResponse {
    private String token;
    private String message;
    private String email;
    private String firstName;
    private String lastName;
    private Role role;
    
    public AuthResponse(String token, String message) {
        this.token = token;
        this.message = message;
    }
    
    public AuthResponse(String token, String message, String email, String firstName, String lastName, Role role) {
        this.token = token;
        this.message = message;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }
}