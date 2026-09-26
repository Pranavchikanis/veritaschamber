package com.veritaschambers.dto.response;

import java.time.LocalDateTime;

public record WebsiteSettingResponse(
        Long id,
        String contactEmail,
        String contactPhone,
        String officeAddress,
        String officeHours,
        String linkedinUrl,
        String twitterUrl,
        LocalDateTime updatedAt
) {
}
