package com.project.ecoWater.auth.app;

import com.project.ecoWater.auth.infrastructure.AuthRequest;
import com.project.ecoWater.auth.infrastructure.AuthResponse;
import com.project.ecoWater.user.domain.User;
import com.project.ecoWater.user.domain.UserRepository;
import com.project.ecoWater.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthApplicationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(User user) {
        if (userRepository.existUserByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email address already in use");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        User saved= userRepository.saveUser(user);

        String token = jwtService.generateToken(saved);

        return AuthResponse.builder()
                .token(token)
                .build();
    }

    public AuthResponse authenticate(AuthRequest authRequest) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authRequest.getEmail(),
                authRequest.getPassword()
        ));

        User user = userRepository.findUserByEmail(authRequest.getEmail()).orElseThrow(()-> new IllegalArgumentException("Email not found"));
        String token = jwtService.generateToken(user);
        System.out.printf("Token: %s\n", token);
        return AuthResponse.builder()
                .token(token)
                .build();
    }

}
