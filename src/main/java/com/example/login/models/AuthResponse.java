package com.example.login.models;

import lombok.Data;

@Data
public class AuthResponse {
    
    private Long id;
    private String userName;
    private String email;
    private String role;
    private String token; // Token JWT

    // Constructor para crear una respuesta con token
    public AuthResponse(User user, String token) {
        this.id = user.getId();
        this.userName = user.getUserName();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.token = token;
    }

}
