package com.milsabores.profile.service;

import com.milsabores.profile.dto.AuthResponse;
import com.milsabores.profile.dto.LoginRequest;
import com.milsabores.profile.dto.RegisterRequest;
import com.milsabores.profile.entity.User;
import com.milsabores.profile.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

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

            final UserDetails userDetails = userService.loadUserByUsername(request.getEmail());
            final String jwt = jwtUtil.generateToken(userDetails);

            System.out.println("=== JWT SECURITY DEBUG ===");
            System.out.println("JWT generated for user: " + request.getEmail());
            System.out.println("JWT Token: " + jwt);
            System.out.println("JWT Expiration: " + jwtUtil.extractExpiration(jwt));
            System.out.println("=== END SECURITY DEBUG ===");

            return new AuthResponse(jwt, "Login successful");
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

        final UserDetails userDetails = userService.loadUserByUsername(user.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails);

        System.out.println("=== JWT SECURITY DEBUG ===");
        System.out.println("JWT generated for new user: " + request.getEmail());
        System.out.println("JWT Token: " + jwt);
        System.out.println("=== END SECURITY DEBUG ===");

        return new AuthResponse(jwt, "User registered successfully");
    }
}
