package org.mohsoft.repository;

import org.mohsoft.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

	// Checks if a different user exists with the same email
	UserProfile findByEmail(String email);	
}
