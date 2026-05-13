package com.edutech.dto;

public class LoginResponse {

    private String token;
    private String role;
    private Long userId;
    private String username;

    public LoginResponse() {
    }

    public LoginResponse(String token, String role, Long userId, String username) {
        this.token = token;
        this.role = role;
        this.userId = userId;
        this.username = username;
    }

    public LoginResponse(String token, String role) {
        this.token = token;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public String getJwtToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setJwtToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}