package com.example.demo.registration.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String getHomePage() {
        return "index2";
    }
	
	@GetMapping("/welcome")
	public String getWelcomePage() {
		return "welcome";
	}
	
	@GetMapping("/admin")
	public String getAdminPage() {
		return "admin";
	}
	
	@GetMapping("/mgr")
	public String getManagerPage() {
		return "manager";
	}
	
	@GetMapping("/common")
	public String getCommonPage() {
		return "common";
	}
	
	@GetMapping("/accessDenied")
	public String getAccessDeniedPage() {
		return "denied";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";		
	}	
}
