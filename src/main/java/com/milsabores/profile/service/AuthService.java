package com.milsabores.profile.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.milsabores.profile.dto.AuthResponse;
import com.milsabores.profile.dto.LoginRequest;
import com.milsabores.profile.dto.RegisterRequest;
import com.milsabores.profile.model.User;
import com.milsabores.profile.security.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            User user = userService.findByEmail(request.getEmail());
            final String jwt = jwtUtil.generateToken(user);

            System.out.println("=== JWT SECURITY DEBUG ===");
            System.out.println("JWT generated for user: " + request.getEmail());
            System.out.println("User ID: " + user.getId());
            System.out.println("User role FROM DB: " + user.getRole());
            System.out.println("User role TYPE: " + user.getRole().getClass().getSimpleName());
            System.out.println("JWT Token: " + jwt);
            System.out.println("JWT Expiration: " + jwtUtil.extractExpiration(jwt));
            
            // Verificar qué se está devolviendo en la response
            AuthResponse response = new AuthResponse(jwt, "Login successful", user.getEmail(), 
                                  user.getFirstName(), user.getLastName(), user.getRole());
            System.out.println("Response role: " + response.getRole());
            System.out.println("=== END SECURITY DEBUG ===");

            return response;
        } catch (Exception e) {
            throw new RuntimeException("Invalid credentials");
        }
    }

    public AuthResponse register(RegisterRequest request) {
        User user = userService.registerUser(
            request.getEmail(),
            request.getPassword(),
            request.getFirstName(),
            request.getLastName()
        );

        final String jwt = jwtUtil.generateToken(user);

        System.out.println("=== JWT SECURITY DEBUG ===");
        System.out.println("JWT generated for new user: " + request.getEmail());
        System.out.println("User role: " + user.getRole());
        System.out.println("JWT Token: " + jwt);
        System.out.println("=== END SECURITY DEBUG ===");

        return new AuthResponse(jwt, "User registered successfully", user.getEmail(),
                              user.getFirstName(), user.getLastName(), user.getRole());
    }
}
