package org.mohsoft;

import java.util.Optional;
import org.mohsoft.model.User;
import org.mohsoft.model.UserProfile;
import org.mohsoft.repository.UserProfileRepository;
import org.mohsoft.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class FileStorageApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileStorageApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner createInitialUser(UserRepository userRepository, PasswordEncoder passwordEncoder, UserProfileRepository userProfileRepository) {
	    return (args) -> {
	        // Check if the user with the desired username already exists in the database
	        Optional<User> existingUser = userRepository.findByUsername("user");

	        // If the user doesn't exist, create and save the user, authority, and user profile
	        
	        if (!existingUser.isPresent()) {
	        	//No user in the database, we create a user and an admin");
	            User user = new User();
	            user.setUsername("user");
	            user.setPassword(passwordEncoder.encode("pass"));
	            user.setEnabled(true);

	            

	            user.setRole("USER");


	            UserProfile userProfile = new UserProfile();
	            userProfile.setFirstName("John");
	            userProfile.setLastName("Doe");
	            userProfile.setEmail("john.doe@example.com");
	            userProfile.setUser(user);
	            

	            user.setUserProfile(userProfile); 

	            userRepository.save(user);

	            userProfileRepository.save(userProfile);
	        }
	        
	        Optional<User> existingAdmin = userRepository.findByUsername("admin");

	        // If the admin doesn't exist, create and save the user, and user profile
	        
	        if (!existingAdmin.isPresent()) {
	     
	            User user = new User();
	            user.setUsername("admin");
	            user.setPassword(passwordEncoder.encode("admin"));
	            user.setEnabled(true);


	            user.setRole("ADMIN");


	            UserProfile userProfile = new UserProfile();
	            userProfile.setFirstName("Jane");
	            userProfile.setLastName("doe");
	            userProfile.setEmail("jane.doe@admin.com");
	            userProfile.setUser(user);
	            

	            user.setUserProfile(userProfile); 

	            userRepository.save(user);

	            userProfileRepository.save(userProfile);
	        }
	       
	    };
	    
	}
	
	
}

