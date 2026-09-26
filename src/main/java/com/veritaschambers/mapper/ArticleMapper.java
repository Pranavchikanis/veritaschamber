package com.veritaschambers.mapper;

import com.veritaschambers.dto.request.ArticleCreateRequest;
import com.veritaschambers.dto.request.ArticleUpdateRequest;
import com.veritaschambers.dto.response.ArticleCategoryResponse;
import com.veritaschambers.dto.response.ArticleResponse;
import com.veritaschambers.dto.response.ArticleSummaryResponse;
import com.veritaschambers.entity.Article;
import com.veritaschambers.entity.ArticleCategory;
import com.veritaschambers.entity.enums.ContentStatus;

import java.time.LocalDateTime;

public class ArticleMapper {

    private ArticleMapper() {
    }

    public static Article toEntity(ArticleCreateRequest request, ArticleCategory category) {
        if (request == null) {
            return null;
        }

        Article article = new Article();
        article.setCategory(category);
        article.setTitle(request.title());
        article.setSlug(request.slug());
        article.setExcerpt(request.excerpt());
        article.setContent(request.content());
        article.setFeaturedImageUrl(request.featuredImageUrl());
        article.setStatus(request.status());
        
        if (request.status() == ContentStatus.PUBLISHED) {
            article.setPublishedAt(LocalDateTime.now());
        }

        return article;
    }

    public static void updateEntity(Article article, ArticleUpdateRequest request, ArticleCategory category) {
        if (article == null || request == null) {
            return;
        }
        
        article.setCategory(category);
        article.setTitle(request.title());
        article.setSlug(request.slug());
        article.setExcerpt(request.excerpt());
        article.setContent(request.content());
        article.setFeaturedImageUrl(request.featuredImageUrl());

        // Update publishedAt logic if status changes
        if (article.getStatus() != ContentStatus.PUBLISHED && request.status() == ContentStatus.PUBLISHED) {
            article.setPublishedAt(LocalDateTime.now());
        } else if (request.status() != ContentStatus.PUBLISHED) {
            article.setPublishedAt(null);
        }

        article.setStatus(request.status());
    }

    public static ArticleCategoryResponse toCategoryResponse(ArticleCategory category) {
        if (category == null) {
            return null;
        }
        return new ArticleCategoryResponse(
                category.getId(),
                category.getName(),
                category.getSlug(),
                category.getDescription()
        );
    }

    public static ArticleResponse toResponse(Article article) {
        if (article == null) {
            return null;
        }

        return new ArticleResponse(
                article.getId(),
                toCategoryResponse(article.getCategory()),
                article.getTitle(),
                article.getSlug(),
                article.getExcerpt(),
                article.getContent(),
                article.getFeaturedImageUrl(),
                article.getStatus(),
                article.getPublishedAt(),
                article.getCreatedAt(),
                article.getUpdatedAt()
        );
    }

    public static ArticleSummaryResponse toSummaryResponse(Article article) {
        if (article == null) {
            return null;
        }

        return new ArticleSummaryResponse(
                article.getId(),
                toCategoryResponse(article.getCategory()),
                article.getTitle(),
                article.getSlug(),
                article.getExcerpt(),
                article.getFeaturedImageUrl(),
                article.getPublishedAt()
        );
    }
}
