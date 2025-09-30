package com.sm.project;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public User register(@RequestBody User user){
        return service.register(user);
    }


    @PostMapping("/login")
    public String login(@RequestBody User user){
        return service.verify(user);
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/csrf-token")
    public CsrfToken getCsrfToken(HttpServletRequest request){
        return (CsrfToken) request.getAttribute("_csrf");
    }

    @PostMapping("/users")
    public User addUser(@RequestBody User user){
        return userRepository.save(user);
    }

    // Update self (authenticated user)
    @PutMapping("/user/{id}")
    public User updateUser(@PathVariable int id, @RequestBody User userDetails){
        return service.updateUser(id, userDetails);
    }

    // Delete user (admin only)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteUser(@PathVariable int id){
        userRepository.deleteById(id);
        return "User deleted successfully";
    }

}