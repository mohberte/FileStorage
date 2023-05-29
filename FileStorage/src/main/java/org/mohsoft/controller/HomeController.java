package org.mohsoft.controller;

import org.mohsoft.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class HomeController {
	
	@Autowired
	UserRepository userRepository;
	

	// Shows main page if no user is authenticated, else, it show the appropriate page
	@GetMapping("/")
    public String home(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            if (AuthorityUtils.authorityListToSet(authentication.getAuthorities()).contains("ROLE_ADMIN")) {
                return "redirect:/lockedAccounts";
            } else {
                return "redirect:/userContent";
            }
        } else {
            return "public/index";
        }
    }
	
	// About page
	@GetMapping("/about")
	public String getAbout() {
	    return "public/about";
	}

	


}
