package com.example.demo.registration.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.registration.model.User;
import com.example.demo.registration.repository.UserRepository;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String emailAddress) throws UsernameNotFoundException {
        
        Optional<User> opt = userRepository.findByEmailAddressWithRoles(emailAddress); //Get User entity with the help of email from UserRepository.

        if (opt.isPresent()) {
            User user = opt.get();

            UserDetails userDetails = new CustomUserDetails(user);

            return userDetails;
        } else {
            throw new UsernameNotFoundException("User with email: " + emailAddress + " not found");
        }
    }	
}
