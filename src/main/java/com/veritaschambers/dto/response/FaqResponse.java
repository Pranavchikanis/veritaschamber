package com.veritaschambers.dto.response;

import java.time.LocalDateTime;

public record FaqResponse(
        Long id,
        String question,
        String answer,
        Integer displayOrder,
        Boolean isActive,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
