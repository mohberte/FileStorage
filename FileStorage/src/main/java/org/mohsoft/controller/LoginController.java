package org.mohsoft.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

	// Gets to the login page, if user authenticated, gets to the home page
	@GetMapping("/login")
	public String loginPage(Authentication authentication) {
		
        if (authentication != null && authentication.isAuthenticated()) {
        	return "redirect:/";
        }
	    return "public/login";
	}
}
