package org.mohsoft.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller

public class LogoutProcessorController {

	@GetMapping("/customLogout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
	    HttpSession session = request.getSession(false);
	    if (session != null) {
	        session.invalidate();
	    }
	    long sessionTimeoutTimestamp =  0;
	    Cookie cookie = new Cookie("sessionTimeoutTimestamp", Long.toString(sessionTimeoutTimestamp));
    	cookie.setMaxAge(3600); // Set cookie to expire in 1 hour
    	cookie.setPath(request.getContextPath() + "/");
    	response.addCookie(cookie);
		    return "/public/logoutSuccess";
		}
	
	@GetMapping("/logoutSuccess")
	public String loginPage2(HttpServletRequest request, HttpServletResponse response) {
	
		 HttpSession session = request.getSession(false);
		    if (session != null) {
		        session.invalidate();
		    }
		    long sessionTimeoutTimestamp =  0;
		    Cookie cookie = new Cookie("sessionTimeoutTimestamp", Long.toString(sessionTimeoutTimestamp));
	    	cookie.setMaxAge(3600); // Set cookie to expire in 1 hour
	    	cookie.setPath(request.getContextPath() + "/");
	    	response.addCookie(cookie);
	    return "/public/index";
	}
}
