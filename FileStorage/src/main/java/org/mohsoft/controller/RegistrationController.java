package org.mohsoft.controller;

import org.mohsoft.model.UserRegistrationDto;
import org.mohsoft.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpServletRequest;


@Controller
@Validated
@RequestMapping("/registration")
public class RegistrationController {
	
	@Autowired
    private UserService userService;

	
	//Makes sure there is no user logged in before letting one register
    @GetMapping
    public String showRegistrationForm(Model model, Authentication authentication) {
		
        if (authentication != null && authentication.isAuthenticated()) {
        	return "redirect:/";
        }
        model.addAttribute("userRegistrationDto", new UserRegistrationDto());
        return "public/registration";
    }
    
    // Validates the submitted form and saves the user if there are no mistakes
    @PostMapping
    public String processRegistrationForm(@ModelAttribute("userRegistrationDto") @Validated UserRegistrationDto userRegistrationDto,
                                          BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
        	
            return "public/registration";
        }
        userService.save(userRegistrationDto);
        

        return "/public/registrationSuccess";
    }
    
   
}
