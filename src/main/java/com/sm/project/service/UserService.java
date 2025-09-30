package com.sm.project.service;

import com.sm.project.Auth.JWTService;
import com.sm.project.constant.Role;
import com.sm.project.entity.User;
import com.sm.project.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public User register(User user){
        user.setPassword(encoder.encode(user.getPassword()));
        if (user.getRole() == null) {
            user.setRole(Role.STUDENT); // or whatever default you want
        }
        return repository.save(user);
    };


    public String verify(User user) {
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
        if(authentication.isAuthenticated()){
            return jwtService.generateToken(user.getUsername());
        }
        else {
            return "failed";
        }
    }

    public User updateUser(int id, User userDetails){
        User existingUser = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Only update allowed fields
        existingUser.setUsername(userDetails.getUsername());
        existingUser.setEmail(userDetails.getEmail());
        existingUser.setFullname(userDetails.getFullname());
        if(userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()){
            existingUser.setPassword(encoder.encode(userDetails.getPassword()));
        }
        if(userDetails.getStatus() != null){
            existingUser.setStatus(userDetails.getStatus());
        }
        return repository.save(existingUser);
    }

    public User getById(Long id) {
        return repository.findById(Math.toIntExact(id))
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

}
