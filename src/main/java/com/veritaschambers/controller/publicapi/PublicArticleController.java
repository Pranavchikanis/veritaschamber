package com.veritaschambers.controller.publicapi;

import com.veritaschambers.dto.response.ArticleResponse;
import com.veritaschambers.dto.response.ArticleSummaryResponse;
import com.veritaschambers.service.ArticleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/public/articles")
public class PublicArticleController {

    private final ArticleService articleService;

    public PublicArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public ResponseEntity<Page<ArticleSummaryResponse>> getPublishedArticles(Pageable pageable) {
        return ResponseEntity.ok(articleService.getPublishedArticles(pageable));
    }

    @GetMapping("/{slug}")
    public ResponseEntity<ArticleResponse> getArticleBySlug(@PathVariable String slug) {
        return ResponseEntity.ok(articleService.getArticleBySlug(slug));
    }
}
