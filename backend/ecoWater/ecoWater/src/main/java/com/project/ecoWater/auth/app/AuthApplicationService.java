package com.project.ecoWater.auth.app;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
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
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

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

    public GoogleIdToken.Payload verifyGoogleToken(String idTokenString) {
        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                    new NetHttpTransport(),
                    JacksonFactory.getDefaultInstance()
            )
                    .setAudience(Collections.singletonList("272820450827-0r5goasrqcpid8n14og9g01m5pao7aof.apps.googleusercontent.com"))
                    .build();
            GoogleIdToken idToken = verifier.verify(idTokenString);
            return idToken != null ? idToken.getPayload() : null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public AuthResponse authenticateWithGoogle(String idTokenString) {
        GoogleIdToken.Payload payload = verifyGoogleToken(idTokenString);
        if (payload == null) {
            throw new IllegalArgumentException("Invalid ID token");
        }

        String email = payload.getEmail();
        String name = (String) payload.get("name");

        Optional<User> optionalUser = userRepository.findUserByEmail(email);

        User user = optionalUser.orElseGet(() -> {
            User newUser = User.builder()
                    .email(email)
                    .user_name(name)
                    .created(Timestamp.valueOf(LocalDateTime.now()))
                    .password("")
                    .build();
            return userRepository.saveUser(newUser);
        });

        String token = jwtService.generateToken(user);
        return AuthResponse.builder().token(token).build();
    }



}
