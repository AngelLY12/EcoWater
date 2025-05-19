package com.project.ecoWater.auth.infrastructure;


import com.project.ecoWater.auth.app.AuthApplicationService;
import com.project.ecoWater.user.domain.User;
import com.project.ecoWater.user.infrastructure.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor()
public class AuthController {
    private final AuthApplicationService authApplicationService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        try {
            AuthResponse authResponse = authApplicationService.authenticate(authRequest);
            return ResponseEntity.ok(authResponse);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest) {
           try {
               User user=UserMapper.registerRequestToUser(registerRequest);

               System.out.println(user);
               return ResponseEntity.ok(authApplicationService.register(user));
           } catch (Exception e) {
               System.out.printf("Error: %s\n", e.getMessage());
               throw new RuntimeException(e);
           }


    }
    @PostMapping("/google")
    public ResponseEntity<AuthResponse> loginWithGoogle(@RequestBody GoogleAuthRequest request) {
        try {
            AuthResponse response = authApplicationService.authenticateWithGoogle(request.getIdToken());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


}

