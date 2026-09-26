package com.veritaschambers.dto.response;

import com.veritaschambers.entity.enums.ConsultationStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record ConsultationResponse(
        Long id,
        String name,
        String email,
        String phone,
        String preferredContactMethod,
        String subject,
        String message,
        LocalDate preferredDate,
        LocalTime preferredTime,
        ConsultationStatus status,
        String adminNotes,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
