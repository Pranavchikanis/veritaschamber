package com.veritaschambers.dto.response;

import com.veritaschambers.entity.enums.ContentStatus;
import java.time.LocalDateTime;

public record TestimonialResponse(
        Long id,
        String clientName,
        String clientTitle,
        String content,
        Integer displayOrder,
        ContentStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
