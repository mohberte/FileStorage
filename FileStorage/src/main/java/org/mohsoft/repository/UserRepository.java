package org.mohsoft.repository;

import java.util.List;
import java.util.Optional;

import org.mohsoft.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {


	 Optional<User> findByUsername(String username);

	 // Shows the admin all the deactivated accounts
	List<User> findByEnabledFalse();
}