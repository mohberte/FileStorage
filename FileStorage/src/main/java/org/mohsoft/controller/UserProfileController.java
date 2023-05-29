package org.mohsoft.controller;

import org.mohsoft.dto.UpdateEmailDto;
import org.mohsoft.dto.UpdateFirstNameDto;
import org.mohsoft.dto.UpdateLastNameDto;
import org.mohsoft.dto.UpdatePasswordDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserProfileController {

	// A user's profile page
		@GetMapping("/userProfile")
		public String userProfile(Model model, HttpSession session) {
		    if (!model.containsAttribute("updateFirstNameDto")) {
		        model.addAttribute("updateFirstNameDto", new UpdateFirstNameDto());
		    }
		    if (!model.containsAttribute("updateLastNameDto")) {
		        model.addAttribute("updateLastNameDto", new UpdateLastNameDto());
		    }
		    if (!model.containsAttribute("updateEmailDto")) {
		        model.addAttribute("updateEmailDto", new UpdateEmailDto());
		    }
		  
		    model.addAttribute("updatePasswordDto", new UpdatePasswordDto());

		    return "shared/userProfile";
		}
}