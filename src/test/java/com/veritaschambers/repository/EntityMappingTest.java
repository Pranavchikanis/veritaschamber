package com.veritaschambers.repository;

import com.veritaschambers.entity.AdminUser;
import com.veritaschambers.entity.Article;
import com.veritaschambers.entity.ArticleCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class EntityMappingTest {

    @Autowired
    private AdminUserRepository adminUserRepository;

    @Autowired
    private ArticleCategoryRepository articleCategoryRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Test
    void shouldSaveAdminUser() {
        AdminUser user = new AdminUser();
        user.setEmail("test@example.com");
        user.setName("Test Admin");
        user.setPasswordHash("hash");
        
        AdminUser saved = adminUserRepository.save(user);
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getCreatedAt()).isNotNull();
    }

    @Test
    void shouldSaveArticleAndCategory() {
        ArticleCategory category = new ArticleCategory();
        category.setName("Legal News");
        category.setSlug("legal-news");
        category.setDescription("News category");
        
        ArticleCategory savedCategory = articleCategoryRepository.save(category);
        assertThat(savedCategory.getId()).isNotNull();

        Article article = new Article();
        article.setTitle("Test Article");
        article.setSlug("test-article");
        article.setContent("Some content");
        article.setCategory(savedCategory);

        Article savedArticle = articleRepository.save(article);
        assertThat(savedArticle.getId()).isNotNull();
        assertThat(savedArticle.getCategory().getId()).isEqualTo(savedCategory.getId());
    }
}
