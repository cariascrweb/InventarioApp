package com.amarillo.store.dto;

public class AuthResponse {
    private String token;
    private boolean success;

    public AuthResponse(String token, boolean success) {
        this.token = token;
        this.success = success;
    }

    // Getters and setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}