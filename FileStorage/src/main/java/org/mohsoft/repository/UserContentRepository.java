package org.mohsoft.repository;

import java.util.List;
import java.util.Optional;


import org.mohsoft.model.UserContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserContentRepository extends JpaRepository<UserContent, Long> {

	Optional<UserContent> findByFileNameAndUserId(String fileName, int userId);

		List<UserContent> findAllByUserId(int id);
}