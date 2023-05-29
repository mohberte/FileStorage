package org.mohsoft.controller;

import org.mohsoft.dto.UpdateEmailDto;
import org.mohsoft.dto.UpdateFirstNameDto;
import org.mohsoft.dto.UpdateLastNameDto;
import org.mohsoft.dto.UpdatePasswordDto;
import org.mohsoft.model.User;
import org.mohsoft.model.UserProfile;
import org.mohsoft.repository.UserProfileRepository;
import org.mohsoft.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class UpdateUserProfileController {

	@Autowired
	private PasswordEncoder passwordEncoder; 

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private UserRepository userRepository;
    
    //Different endpoints to update the user info

    @PostMapping("/update_first_name")
    public String updateFirstName(@Valid @ModelAttribute UpdateFirstNameDto updateFirstNameDto, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "redirect:/userProfile";
        }

        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");
        if (userProfile != null) {
            userProfile.setFirstName(updateFirstNameDto.getFirstName());

            userProfileRepository.save(userProfile);
            session.setAttribute("userProfile", userProfile);
        }
        return "redirect:/userProfile";
    }

    @PostMapping("/update_last_name")
    public String updateLastName(@Valid @ModelAttribute UpdateLastNameDto updateLastNameDto, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "redirect:/userProfile";
        }

        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");
        if (userProfile != null) {
            userProfile.setLastName(updateLastNameDto.getLastName());

            userProfileRepository.save(userProfile);
            session.setAttribute("userProfile", userProfile);
        }
        return "redirect:/userProfile";
    }

    @PostMapping("/update_email")
    public String updateEmail(@Valid @ModelAttribute UpdateEmailDto updateEmailDto, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "redirect:/userProfile";
        }

        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");
        if (userProfile != null) {
            userProfile.setEmail(updateEmailDto.getEmail());

            userProfileRepository.save(userProfile);
            session.setAttribute("userProfile", userProfile);
        }
        return "redirect:/userProfile";
    }

    @PostMapping("/update_password")
    public String updatePassword(@Valid @ModelAttribute UpdatePasswordDto updatePasswordDto, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "redirect:/userProfile";
        }

        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");
        if (userProfile != null) {
            User user = userProfile.getUser();
            user.setPassword(passwordEncoder.encode(updatePasswordDto.getPassword()));

            userRepository.save(user);
            session.setAttribute("userProfile", userProfile);
        }
        return "redirect:/userProfile";
    }

}
