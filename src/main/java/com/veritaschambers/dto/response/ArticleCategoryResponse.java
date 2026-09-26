package com.veritaschambers.dto.response;

public record ArticleCategoryResponse(
        Long id,
        String name,
        String slug,
        String description
) {
}
