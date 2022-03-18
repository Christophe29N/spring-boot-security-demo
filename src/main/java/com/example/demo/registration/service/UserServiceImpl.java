package com.example.demo.registration.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.registration.model.Role;
import com.example.demo.registration.model.RoleEnum;
import com.example.demo.registration.model.User;
import com.example.demo.registration.repository.RoleRepository;
import com.example.demo.registration.repository.UserRepository;
import com.example.demo.registration.web.dto.UserRegistrationDto;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;
	
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
		super();
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}

	@Override
	public User save(UserRegistrationDto registrationDto) {
		User user = new User(registrationDto.getFirstName(),
				registrationDto.getLastName(),
				registrationDto.getEmailAddress(),
				passwordEncoder.encode(registrationDto.getPassword()));
		
		Role roleUser;
		
		Optional<Role> opt = roleRepository.findByName(RoleEnum.CONTRIBUTOR.name());
		  if (opt.isPresent()) {
	            roleUser = opt.get();
		  } else {
		        Role role = new Role(RoleEnum.CONTRIBUTOR.name());
		        roleUser = roleRepository.save(role);		        
		  }
		      
		user.addRole(roleUser);
		
		User savedUser = userRepository.save(user);
		return savedUser;
	}

	/**
	 * UserDetailService interface implementation
	 */
	@Override
	public UserDetails loadUserByUsername(String emailAddress) throws UsernameNotFoundException {

		Optional<User> opt = userRepository.findByEmailAddressWithRoles(emailAddress); //Get User entity with the help of email from UserRepository.

		if (opt.isPresent()) {
			User user = opt.get();
			
			UserDetails userDetails = new CustomUserDetails(user);
			
			return userDetails; //Return spring defined User object which is an implementation of UserDetails
		} else {
			throw new UsernameNotFoundException("User with email: " + emailAddress + " not found");
		}
	}
}
