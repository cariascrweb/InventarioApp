package com.amarillo.store.service;

import com.amarillo.store.dto.AuthRequest;
import com.amarillo.store.dto.AuthResponse;
import com.amarillo.store.dto.GoogleTokenRequest;
import com.amarillo.store.entity.User;
import com.amarillo.store.repository.UserRepository;
import com.amarillo.store.security.JwtUtil;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final JsonFactory jsonFactory;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String googleClientId;

    public AuthServiceImpl(UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.jsonFactory = JacksonFactory.getDefaultInstance();
    }

    @Override
    public AuthResponse authenticate(AuthRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user.getUsername());
        return new AuthResponse(token, true);
    }

    @Override
    public AuthResponse authenticateWithGoogle(GoogleTokenRequest request) {
        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                    new NetHttpTransport(), jsonFactory)
                    .setAudience(Collections.singletonList(googleClientId))
                    .build();

            GoogleIdToken idToken = verifier.verify(request.getToken());
            if (idToken == null) {
                throw new BadCredentialsException("Invalid Google token");
            }

            GoogleIdToken.Payload payload = idToken.getPayload();
            String email = payload.getEmail();
            String name = (String) payload.get("name");

            User user = userRepository.findByEmail(email)
                    .orElseGet(() -> createGoogleUser(email, name));

            String token = jwtUtil.generateToken(user.getUsername());
            return new AuthResponse(token, true);

        } catch (Exception e) {
            throw new BadCredentialsException("Failed to authenticate with Google", e);
        }
    }

    private User createGoogleUser(String email, String name) {
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setProvider(User.AuthProvider.GOOGLE);
        user.setEnabled(true);
        return userRepository.save(user);
    }
}