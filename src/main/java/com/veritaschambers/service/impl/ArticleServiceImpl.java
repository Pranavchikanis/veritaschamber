package com.veritaschambers.service.impl;

import com.veritaschambers.dto.request.ArticleCreateRequest;
import com.veritaschambers.dto.request.ArticleUpdateRequest;
import com.veritaschambers.dto.response.ArticleResponse;
import com.veritaschambers.dto.response.ArticleSummaryResponse;
import com.veritaschambers.entity.Article;
import com.veritaschambers.entity.ArticleCategory;
import com.veritaschambers.entity.enums.ContentStatus;
import com.veritaschambers.exception.BusinessValidationException;
import com.veritaschambers.exception.ResourceNotFoundException;
import com.veritaschambers.mapper.ArticleMapper;
import com.veritaschambers.repository.ArticleCategoryRepository;
import com.veritaschambers.repository.ArticleRepository;
import com.veritaschambers.service.ArticleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleCategoryRepository categoryRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository, ArticleCategoryRepository categoryRepository) {
        this.articleRepository = articleRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public ArticleResponse createArticle(ArticleCreateRequest request) {
        // Validate slug uniqueness (Optional: can add custom query `existsBySlug`)
        // For simplicity, relying on DataIntegrityViolationException is possible, but proactive check is better.

        ArticleCategory category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        Article article = ArticleMapper.toEntity(request, category);
        
        try {
            Article saved = articleRepository.save(article);
            return ArticleMapper.toResponse(saved);
        } catch (Exception e) {
            throw new BusinessValidationException("Could not save article. Slug might already exist.");
        }
    }

    @Override
    @Transactional
    public ArticleResponse updateArticle(Long id, ArticleUpdateRequest request) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Article not found"));

        ArticleCategory category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        ArticleMapper.updateEntity(article, request, category);

        try {
            Article updated = articleRepository.save(article);
            return ArticleMapper.toResponse(updated);
        } catch (Exception e) {
            throw new BusinessValidationException("Could not update article. Slug might already exist.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArticleSummaryResponse> getAllArticles(Pageable pageable) {
        return articleRepository.findAll(pageable)
                .map(ArticleMapper::toSummaryResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArticleSummaryResponse> getPublishedArticles(Pageable pageable) {
        return articleRepository.findByStatus(ContentStatus.PUBLISHED, pageable)
                .map(ArticleMapper::toSummaryResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public ArticleResponse getArticleById(Long id) {
        return articleRepository.findById(id)
                .map(ArticleMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Article not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public ArticleResponse getArticleBySlug(String slug) {
        return articleRepository.findBySlug(slug)
                .map(ArticleMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Article not found"));
    }

    @Override
    @Transactional
    public void deleteArticle(Long id) {
        if (!articleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Article not found");
        }
        articleRepository.deleteById(id);
    }
}
