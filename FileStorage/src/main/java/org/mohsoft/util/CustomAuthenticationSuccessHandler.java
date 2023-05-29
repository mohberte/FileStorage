package org.mohsoft.util;
import org.mohsoft.model.User;
import org.mohsoft.model.UserProfile;
import org.mohsoft.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;


@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	@Autowired
	private UserRepository userRepository;
	
	@Value("${session.timeout}")
	private int sessionTimeout;

	public CustomAuthenticationSuccessHandler(UserRepository userRepository)
	{
		this.userRepository = userRepository;
	}
	@Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        String username = request.getParameter("username");
       
        Optional<User> user = userRepository.findByUsername(username);

        
        
            User userObj = user.get();
            // Increment login attempts
            userObj.setLogin_attempts(0);
            userRepository.save(userObj);
       
             request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());

            UserProfile userProfile = userObj.getUserProfile(); // Get the user profile object

         
            // Add the user profile object to the model
            request.getSession().setAttribute("userProfile", userProfile);

            
            if(userObj.isEnabled())
            {	
            	// Set a cookie with the session timeout timestamp
                long sessionTimeoutTimestamp =  (1500);
             
                Cookie cookie = new Cookie("sessionTimeoutTimestamp", Long.toString(sessionTimeoutTimestamp));
            	cookie.setMaxAge(3600); // Set cookie to expire in 1 hour
            	cookie.setPath(request.getContextPath() + "/");
            	response.addCookie(cookie);
              
                
                if (AuthorityUtils.authorityListToSet(authentication.getAuthorities()).contains("ROLE_ADMIN")) {
                    response.sendRedirect("/lockedAccounts");
                } else {
                    response.sendRedirect("/userContent");
                }
            }
        else {
        response.sendRedirect("/logoutSuccess"); // Redirect to the home page after successful authentication
        }
    }
}
