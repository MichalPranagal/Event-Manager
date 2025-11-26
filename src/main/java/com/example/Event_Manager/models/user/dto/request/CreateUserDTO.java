package com.example.Event_Manager.models.user.dto.request;

import com.example.Event_Manager.models.user.enums.Role;
import jakarta.validation.constraints.*;

public record CreateUserDTO(
        @NotBlank(message = "First name is required")
        @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
        String firstName,

        @NotBlank(message = "Last name is required")
        @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
        String lastName,

        @NotBlank(message = "Email is required")
        @Email(message = "Email format is invalid")
        String email,

        @NotBlank(message = "Phone number is required")
        @Pattern(regexp = "^\\d{9}$", message = "Phone number must consist of exactly 9 digits")
        String phoneNumber,

        @NotBlank(message = "Password is required")
        @Size(min = 6, message = "Password must be at least 6 characters long")
        String password,

        @NotNull(message = "Role is required for admin creation")
        Role role
) {}
