package com.veritaschambers.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LawyerProfileUpdateRequest(
        @NotBlank(message = "Name is required")
        @Size(max = 255)
        String name,

        @NotBlank(message = "Title is required")
        @Size(max = 255)
        String title,

        String biography,
        String credentials,

        @Email(message = "Must be a valid email address")
        @Size(max = 255)
        String email,

        @Size(max = 50)
        String phone,

        @Size(max = 500)
        String profileImageUrl
) {
}
