package com.veritaschambers.dto.response;

import com.veritaschambers.entity.enums.ContentStatus;
import java.time.LocalDateTime;

public record PracticeAreaResponse(
        Long id,
        String title,
        String slug,
        String shortDescription,
        String description,
        String iconRef,
        Integer displayOrder,
        ContentStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
