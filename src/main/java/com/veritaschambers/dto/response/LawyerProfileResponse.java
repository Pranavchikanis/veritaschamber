package com.veritaschambers.dto.response;

import java.time.LocalDateTime;

public record LawyerProfileResponse(
        Long id,
        String name,
        String title,
        String biography,
        String credentials,
        String email,
        String phone,
        String profileImageUrl,
        LocalDateTime updatedAt
) {
}
