package com.cloud.controller;

import com.cloud.dto.AppUserDTO;
import com.cloud.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST Controller for user auth (DISABLED - Using AuthServlet instead)
 */
// @RestController
// @RequestMapping("/api/auth")
public class AppUserController {

    // @Autowired
    // private AppUserService userService;

    /**
     * POST /auth/login
     * Body: { "username": "...", "password": "..." }
     * Returns: user profile (no password) on success, 401 on failure
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        try {
            String username = body.get("username");
            String password = body.get("password");
            AppUserDTO user = userService.login(username, password);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * POST /auth/register
     * Body: { "displayName": "...", "email": "...", "role": "..." }
     * Returns: generated username + password (shown once)
     */
    @PostMapping("/register")
    public ResponseEntity<AppUserDTO> register(@RequestBody Map<String, String> body) {
        String displayName = body.get("displayName");
        String email       = body.get("email");
        String role        = body.get("role");
        AppUserDTO created = userService.register(displayName, email, role);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * GET /auth/users
     * Returns all registered users (superadmin only use)
     */
    @GetMapping("/users")
    public ResponseEntity<List<AppUserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
}
