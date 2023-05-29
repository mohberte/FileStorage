package org.mohsoft.model;


import org.mohsoft.validation.UniqueEmail;
import org.mohsoft.validation.UniqueUsername;
import org.mohsoft.validation.ValidPassword;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class UserRegistrationDto {
	
	//DTO used during a user registration
	@UniqueUsername
    private String userName;

    @ValidPassword
    private String password;
    private String matchingPassword;


	@Email
	@UniqueEmail
    @NotEmpty
    private String email;

	public String getUsername() {
		return userName;
	}

	public void setUsername(String username) {
		this.userName = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMatchingPassword() {
		return matchingPassword;
	}

	public void setMatchingPassword(String matchingPassword) {
		this.matchingPassword = matchingPassword;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "UserRegistrationDto [username=" + userName + ", password=" + password + ", matchingPassword="
				+ matchingPassword + ", email=" + email + "]";
	}

}
