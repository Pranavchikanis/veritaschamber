package com.veritaschambers.dto.response;

import com.veritaschambers.entity.enums.MessageStatus;

import java.time.LocalDateTime;

public record ContactMessageResponse(
        Long id,
        String name,
        String email,
        String phone,
        String subject,
        String message,
        MessageStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
