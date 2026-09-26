package com.veritaschambers.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalTime;

public record ConsultationCreateRequest(
        @NotBlank(message = "Name is required")
        @Size(max = 255)
        String name,

        @NotBlank(message = "Email is required")
        @Email(message = "Must be a valid email address")
        @Size(max = 255)
        String email,

        @NotBlank(message = "Phone is required")
        @Pattern(regexp = "^\\+?[0-9\\-\\s]+$", message = "Must be a valid phone number format")
        @Size(max = 50)
        String phone,

        @Size(max = 50)
        String preferredContactMethod,

        @Size(max = 255)
        String subject,

        @NotBlank(message = "Message is required")
        @Size(max = 5000, message = "Message must not exceed 5000 characters")
        String message,

        LocalDate preferredDate,

        LocalTime preferredTime
) {
}
