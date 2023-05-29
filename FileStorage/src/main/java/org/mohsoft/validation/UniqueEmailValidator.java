package org.mohsoft.validation;

import org.mohsoft.model.UserProfile;
import org.mohsoft.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        UserProfile existingUser = userProfileRepository.findByEmail(email);
        return existingUser == null;
    }

}
