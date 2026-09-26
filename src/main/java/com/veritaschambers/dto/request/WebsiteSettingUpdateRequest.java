package com.veritaschambers.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record WebsiteSettingUpdateRequest(
        @NotBlank(message = "Contact email is required")
        @Email(message = "Must be a valid email address")
        @Size(max = 255)
        String contactEmail,

        @NotBlank(message = "Contact phone is required")
        @Pattern(regexp = "^\\+?[0-9\\-\\s]+$", message = "Must be a valid phone number format")
        @Size(max = 50)
        String contactPhone,

        @NotBlank(message = "Office address is required")
        @Size(max = 500)
        String officeAddress,

        @Size(max = 255)
        String officeHours,

        @Size(max = 255)
        String linkedinUrl,

        @Size(max = 255)
        String twitterUrl
) {
}
