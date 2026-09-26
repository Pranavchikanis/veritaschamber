package com.veritaschambers.service;

import com.veritaschambers.dto.request.ArticleCreateRequest;
import com.veritaschambers.dto.request.ArticleUpdateRequest;
import com.veritaschambers.dto.response.ArticleResponse;
import com.veritaschambers.dto.response.ArticleSummaryResponse;
import com.veritaschambers.entity.Article;
import com.veritaschambers.entity.ArticleCategory;
import com.veritaschambers.entity.enums.ContentStatus;
import com.veritaschambers.exception.BusinessValidationException;
import com.veritaschambers.exception.ResourceNotFoundException;
import com.veritaschambers.repository.ArticleCategoryRepository;
import com.veritaschambers.repository.ArticleRepository;
import com.veritaschambers.service.impl.ArticleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private ArticleCategoryRepository categoryRepository;

    @InjectMocks
    private ArticleServiceImpl articleService;

    private ArticleCategory category;
    private Article article;

    @BeforeEach
    void setUp() {
        category = new ArticleCategory();
        category.setId(1L);
        category.setName("Legal Updates");
        category.setSlug("legal-updates");

        article = new Article();
        article.setId(1L);
        article.setTitle("Test Article");
        article.setSlug("test-article");
        article.setContent("Test Content");
        article.setCategory(category);
        article.setStatus(ContentStatus.DRAFT);
    }

    @Test
    void createArticle_Success() {
        ArticleCreateRequest request = new ArticleCreateRequest(1L, "New Article", "new-article", "Excerpt", "Content", null, ContentStatus.PUBLISHED);
        
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(articleRepository.save(any(Article.class))).thenAnswer(i -> {
            Article a = i.getArgument(0);
            a.setId(2L);
            return a;
        });

        ArticleResponse response = articleService.createArticle(request);

        assertNotNull(response);
        assertEquals(2L, response.id());
        assertEquals("New Article", response.title());
        verify(articleRepository, times(1)).save(any(Article.class));
    }

    @Test
    void createArticle_CategoryNotFound_ShouldThrow() {
        ArticleCreateRequest request = new ArticleCreateRequest(99L, "New Article", "new-article", "Excerpt", "Content", null, ContentStatus.PUBLISHED);
        
        when(categoryRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> articleService.createArticle(request));
        verify(articleRepository, never()).save(any(Article.class));
    }

    @Test
    void updateArticle_Success() {
        ArticleUpdateRequest request = new ArticleUpdateRequest(1L, "Updated Title", "updated-slug", "Excerpt", "Updated Content", null, ContentStatus.PUBLISHED);

        when(articleRepository.findById(1L)).thenReturn(Optional.of(article));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(articleRepository.save(any(Article.class))).thenReturn(article);

        ArticleResponse response = articleService.updateArticle(1L, request);

        assertNotNull(response);
        assertEquals("Updated Title", response.title());
        assertEquals(ContentStatus.PUBLISHED, article.getStatus());
        verify(articleRepository, times(1)).save(article);
    }

    @Test
    void getPublishedArticles_ShouldReturnOnlyPublished() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Article> page = new PageImpl<>(List.of(article));
        
        when(articleRepository.findByStatus(ContentStatus.PUBLISHED, pageable)).thenReturn(page);

        Page<ArticleSummaryResponse> result = articleService.getPublishedArticles(pageable);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        verify(articleRepository, times(1)).findByStatus(ContentStatus.PUBLISHED, pageable);
    }

    @Test
    void getArticleBySlug_NotFound_ShouldThrow() {
        when(articleRepository.findBySlug("unknown")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> articleService.getArticleBySlug("unknown"));
    }
}
