package com.veritaschambers.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ContactMessageCreateRequest(
        @NotBlank(message = "Name is required")
        @Size(max = 255)
        String name,

        @NotBlank(message = "Email is required")
        @Email(message = "Must be a valid email address")
        @Size(max = 255)
        String email,

        @Pattern(regexp = "^\\+?[0-9\\-\\s]*$", message = "Must be a valid phone number format if provided")
        @Size(max = 50)
        String phone,

        @Size(max = 255)
        String subject,

        @NotBlank(message = "Message is required")
        @Size(max = 5000, message = "Message must not exceed 5000 characters")
        String message
) {
}
