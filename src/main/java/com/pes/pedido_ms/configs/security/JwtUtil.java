package com.pes.pedido_ms.configs.security;

import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
    private String token;
    private String userId;
    private String email;
    private String role;

    public void setJwtDetails(String token, String userId, String email, String role) {
        this.token = token;
        this.userId = userId;
        this.email = email;
        this.role = role;
    }

    public String token() {
        return token;
    }

    public String userId() {
        return userId;
    }

    public String email() {
        return email;
    }

    public String role() {
        return role;
    }
}

    