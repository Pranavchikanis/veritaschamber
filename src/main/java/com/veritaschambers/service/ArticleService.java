package com.veritaschambers.service;

import com.veritaschambers.dto.request.ArticleCreateRequest;
import com.veritaschambers.dto.request.ArticleUpdateRequest;
import com.veritaschambers.dto.response.ArticleResponse;
import com.veritaschambers.dto.response.ArticleSummaryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleService {
    ArticleResponse createArticle(ArticleCreateRequest request);
    ArticleResponse updateArticle(Long id, ArticleUpdateRequest request);
    Page<ArticleSummaryResponse> getAllArticles(Pageable pageable);
    Page<ArticleSummaryResponse> getPublishedArticles(Pageable pageable);
    ArticleResponse getArticleById(Long id);
    ArticleResponse getArticleBySlug(String slug);
    void deleteArticle(Long id);
}
