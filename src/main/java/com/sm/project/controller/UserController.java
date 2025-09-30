package com.sm.project.controller;

import com.sm.project.entity.User;
import com.sm.project.dao.UserRepository;
import com.sm.project.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private UserRepository userRepository;

    //  Utility method to build consistent response
    private Map<String, Object> buildResponse(String status, String message, Object data) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", status);
        response.put("message", message);
        if (data != null) response.put("data", data);
        return response;
    }

    //  Register user
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            User saved = service.register(user);
            return ResponseEntity.ok(buildResponse("success", " User registered successfully", saved));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(buildResponse("error", " Failed to register user: " + e.getMessage(), null));
        }
    }

    //  Login user
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            String token = service.verify(user);
            if (token != null) {
                return ResponseEntity.ok(buildResponse("success", " Login successful", token));
            } else {
                return ResponseEntity.badRequest().body(buildResponse("error", " Invalid username or password", null));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(buildResponse("error", " Login failed: " + e.getMessage(), null));
        }
    }

    //  Get all users (admin only)
    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getUsers() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            return ResponseEntity.ok(buildResponse("success", "No users found", users));
        }
        return ResponseEntity.ok(buildResponse("success", " Users retrieved successfully", users));
    }

    //  CSRF token (if needed)
    @GetMapping("/csrf-token")
    public CsrfToken getCsrfToken(HttpServletRequest request) {
        return (CsrfToken) request.getAttribute("_csrf");
    }

    //  Add user (open endpoint - usually admin should do this)
    @PostMapping("/users")
    public ResponseEntity<?> addUser(@RequestBody User user) {
        try {
            User saved = userRepository.save(user);
            return ResponseEntity.ok(buildResponse("success", " User added successfully", saved));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(buildResponse("error", " Failed to add user: " + e.getMessage(), null));
        }
    }

    // ✅ Update user
    @PutMapping("/user/{id}")
    public ResponseEntity<?> updateUser(@PathVariable int id, @RequestBody User userDetails) {
        try {
            User updated = service.updateUser(id, userDetails);
            return ResponseEntity.ok(buildResponse("success", " User updated successfully", updated));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(buildResponse("error", " Failed to update user: " + e.getMessage(), null));
        }
    }

    //  Delete user (admin only)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable int id) {
        try {
            userRepository.deleteById(id);
            return ResponseEntity.ok(buildResponse("success", " User deleted successfully", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(buildResponse("error", " Failed to delete user: " + e.getMessage(), null));
        }
    }
}
