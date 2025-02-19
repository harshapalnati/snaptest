package com.snaptest.api;

import com.snaptest.security.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtUtil jwtUtil;

    // Inject values from application.properties
    @Value("${app.auth.username:}")
    private String allowedUsername;

    @Value("${app.auth.password:}")
    private String allowedPassword;

    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestParam String username, @RequestParam String password) {
        if (allowedUsername.isEmpty() || allowedPassword.isEmpty()) {
            return ResponseEntity.status(500).body(Map.of("error", "Authentication properties not set"));
        }

        if (!username.equals(allowedUsername) || !password.equals(allowedPassword)) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid username or password"));
        }

        String token = jwtUtil.generateToken(username);
        return ResponseEntity.ok(Map.of("token", token));
    }
}
