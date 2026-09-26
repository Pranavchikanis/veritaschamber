package com.veritaschambers.dto.request;

import com.veritaschambers.entity.enums.ContentStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ArticleUpdateRequest(
        @NotNull(message = "Category ID is required")
        Long categoryId,

        @NotBlank(message = "Title is required")
        @Size(max = 255)
        String title,

        @NotBlank(message = "Slug is required")
        @Pattern(regexp = "^[a-z0-9-]+$", message = "Slug must contain only lowercase letters, numbers, and hyphens")
        @Size(max = 255)
        String slug,

        String excerpt,

        @NotBlank(message = "Content is required")
        String content,

        @Size(max = 500)
        String featuredImageUrl,

        @NotNull(message = "Status is required")
        ContentStatus status
) {
}
