package org.mohsoft.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private int loginAttempt;


    @OneToOne(mappedBy = "userProfile")
    private User user;
    
    // Getters and setters

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	 public int getLoginAttempt() {
	        return loginAttempt;
	    }

	    public void setLoginAttempt(int loginAttempt) {
	        this.loginAttempt = loginAttempt;
	    }

		@Override
		public String toString() {
			return "UserProfile [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
					+ ", loginAttempt=" + loginAttempt + "]";
		}
	    
}
