package com.veritaschambers.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record FaqCreateRequest(
        @NotBlank(message = "Question is required")
        String question,

        @NotBlank(message = "Answer is required")
        String answer,

        @NotNull(message = "Display order is required")
        Integer displayOrder,

        @NotNull(message = "Active status is required")
        Boolean isActive
) {
}
