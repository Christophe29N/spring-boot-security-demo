package com.example.demo.registration.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.demo.registration.model.User;
import com.example.demo.registration.web.dto.UserRegistrationDto;

public interface UserService extends UserDetailsService {
	User save(UserRegistrationDto registrationDto);
	
}
