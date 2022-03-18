package com.example.demo.registration.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.registration.service.UserService;
import com.example.demo.registration.web.dto.UserRegistrationDto;

@Controller
public class UserRegistrationController {
	private UserService userService;

	public UserRegistrationController(UserService userService) {
		super();
		this.userService = userService;
	}
	
	@ModelAttribute("user")
	public UserRegistrationDto userRegistrationDto() {
		UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        return userRegistrationDto;
	}
	
	/**
	 * Http Get request will return view used for registration
	 * @return
	 */
	@GetMapping("/register")
	public String showRegistrationForm(/*Model model*/) {
		// model.addAttribute("user", new UserRegistrationDto()); //équivalent à déclarer @ModelAttribute("user")
		return "register";
	}
	
	/**
	 * Register new user account
	 * @param registrationDto
	 * @return
	 */
    @PostMapping("/register")
	public String registerUserAccount(@ModelAttribute("user") UserRegistrationDto registrationDto) {		
		userService.save(registrationDto);
		
		return "redirect:/register?success";
	}
    
    
    //@AuthenticationPrincipal ShopmeUserDetails userDetails,
    
}