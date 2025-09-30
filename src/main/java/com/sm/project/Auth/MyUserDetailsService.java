package com.sm.project.Auth;

import com.sm.project.dao.UserRepository;
import com.sm.project.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsernameIgnoreCase(username);
        if(user==null){
            throw new UsernameNotFoundException("user not found");
        }

        return new UserPrincipal(user);
    }
}
