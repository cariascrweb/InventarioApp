package com.amarillo.store.service;

import com.amarillo.store.dto.AuthRequest;
import com.amarillo.store.dto.AuthResponse;
import com.amarillo.store.dto.GoogleTokenRequest;

public interface AuthService {
    AuthResponse authenticate(AuthRequest request);

    AuthResponse authenticateWithGoogle(GoogleTokenRequest request);
}