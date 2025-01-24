package com.amarillo.store.controller;

import com.amarillo.store.dto.AuthRequest;
import com.amarillo.store.dto.AuthResponse;
import com.amarillo.store.dto.GoogleTokenRequest;
import com.amarillo.store.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @PostMapping("/google")
    public ResponseEntity<AuthResponse> googleLogin(@RequestBody GoogleTokenRequest request) {
        return ResponseEntity.ok(authService.authenticateWithGoogle(request));
    }

    @GetMapping("/verify")
    public ResponseEntity<Void> verifyToken() {
        // If the request reaches here, it means the token is valid
        return ResponseEntity.ok().build();
    }
}