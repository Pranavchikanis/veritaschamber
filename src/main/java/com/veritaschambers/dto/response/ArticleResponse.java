package com.veritaschambers.dto.response;

import com.veritaschambers.entity.enums.ContentStatus;
import java.time.LocalDateTime;

public record ArticleResponse(
        Long id,
        ArticleCategoryResponse category,
        String title,
        String slug,
        String excerpt,
        String content,
        String featuredImageUrl,
        ContentStatus status,
        LocalDateTime publishedAt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
