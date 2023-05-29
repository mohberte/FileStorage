package org.mohsoft.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UpdatePasswordDto {
    @NotBlank(message = "Password is required")
    @Size(min = 2, max = 30, message = "Password must be between 2 and 30 characters")
    
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

