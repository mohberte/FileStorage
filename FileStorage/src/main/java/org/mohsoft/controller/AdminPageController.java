package org.mohsoft.controller;

import java.util.List;
import java.util.Optional;

import org.mohsoft.model.User;
import org.mohsoft.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminPageController {

	@Autowired
	UserRepository userRepository;
	
	//Redirects here when an unauthenticated user tries to access a restricted page
	@GetMapping("/accessDenied")
	public String accessDenied() {
	    return "public/accessDenied";
	}
	
	//Main admin page, displays the accounts that have been locked
	@GetMapping("/lockedAccounts")
	public String lockedAccounts(Model model) {
	   List<User> lockedUsers = userRepository.findByEnabledFalse();
	  
	   model.addAttribute("lockedUsers", lockedUsers);
	    return "admin/lockedAccounts";
	}
	
	//Admin function that shows all the locked accounts and lets the admin decide which one to reactivate
	 @PostMapping("/reactivateAccounts")
	    public String reactivateAccounts(@RequestParam(name = "reactivateUsers", required = false) List<String> usernames) {
	        if (usernames != null && !usernames.isEmpty()) {
	            for (String username : usernames) {
	                Optional<User> userOptional = userRepository.findByUsername(username);
	                if (userOptional.isPresent()) {
	                    User user = userOptional.get();
	                    user.setEnabled(true);
	                    userRepository.save(user);
	                }
	            }
	        }
	        return "/public/index";
	    }

}
