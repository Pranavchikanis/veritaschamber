package com.veritaschambers.dto.request;

import com.veritaschambers.entity.enums.ContentStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record PracticeAreaCreateRequest(
        @NotBlank(message = "Title is required")
        @Size(max = 255)
        String title,

        @NotBlank(message = "Slug is required")
        @Pattern(regexp = "^[a-z0-9-]+$", message = "Slug must contain only lowercase letters, numbers, and hyphens")
        @Size(max = 255)
        String slug,

        @NotBlank(message = "Short description is required")
        @Size(max = 500)
        String shortDescription,

        String description,

        @Size(max = 100)
        String iconRef,

        @NotNull(message = "Display order is required")
        Integer displayOrder,

        @NotNull(message = "Status is required")
        ContentStatus status
) {
}
