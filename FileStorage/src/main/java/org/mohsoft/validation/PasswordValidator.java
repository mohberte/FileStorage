package org.mohsoft.validation;



import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;



public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    // Defining custom password requirements using regex
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{5,}$");

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
   }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) {
         return false;
        }
        return PASSWORD_PATTERN.matcher(password).matches();
    }
}

