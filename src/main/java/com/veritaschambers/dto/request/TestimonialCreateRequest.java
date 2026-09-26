package com.veritaschambers.dto.request;

import com.veritaschambers.entity.enums.ContentStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TestimonialCreateRequest(
        @NotBlank(message = "Client name is required")
        @Size(max = 255)
        String clientName,

        @Size(max = 255)
        String clientTitle,

        @NotBlank(message = "Content is required")
        String content,

        @NotNull(message = "Display order is required")
        Integer displayOrder,

        @NotNull(message = "Status is required")
        ContentStatus status
) {
}
