package org.mohsoft.service;

import org.mohsoft.model.User;
import org.mohsoft.model.UserProfile;
import org.mohsoft.repository.UserProfileRepository;
import org.mohsoft.model.UserRegistrationDto;
import org.mohsoft.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void save(UserRegistrationDto userRegistrationDto) {
 
    	// Creates a user through the registration form
        User user = new User();
        user.setUsername(userRegistrationDto.getUsername());	
        user.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));
        user.setRole("USER");
        user.setEnabled(true);


        UserProfile userProfile = new UserProfile();
        userProfile.setEmail(userRegistrationDto.getEmail());
   
        userProfileRepository.save(userProfile);
        user.setUserProfile(userProfile);
        userRepository.save(user);
    }
}
