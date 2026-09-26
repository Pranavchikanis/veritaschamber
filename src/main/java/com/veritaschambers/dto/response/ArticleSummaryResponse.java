package com.veritaschambers.dto.response;

import java.time.LocalDateTime;

public record ArticleSummaryResponse(
        Long id,
        ArticleCategoryResponse category,
        String title,
        String slug,
        String excerpt,
        String featuredImageUrl,
        LocalDateTime publishedAt
) {
}
