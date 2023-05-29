package org.mohsoft.util;

import java.io.IOException;
import java.util.Optional;

import org.mohsoft.model.User;
import org.mohsoft.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
	public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
		
		@Autowired
		private UserRepository userRepository;

		public CustomAuthenticationFailureHandler(UserRepository userRepository)
		{
			this.userRepository = userRepository;
		}

	    @Override
	    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
	            AuthenticationException exception) throws IOException, ServletException {
	    	
	    	HttpSession session = request.getSession();
	        session.setAttribute("loginError", "Invalid username or password");

	        String username = request.getParameter("username");
	       
	        Optional<User> user = userRepository.findByUsername(username);

	        if (user.isPresent()) {
	        	 User userObj = user.get();
	            // Increment login attempts
	            userObj.setLogin_attempts(userObj.getLogin_attempts() + 1);

	            // Disable user if login attempts reaches 4
	            if (userObj.getLogin_attempts() >= 4) {
	                userObj.setEnabled(false);
	                userRepository.save(userObj);
	           } else {
	            	userRepository.save(userObj);
	            }
	        }
	      response.sendRedirect("/public/login"); // Redirect to the home page after successful authentication
	    }

	}
