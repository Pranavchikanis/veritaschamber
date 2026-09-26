package com.veritaschambers.config;

import com.veritaschambers.entity.*;
import com.veritaschambers.entity.enums.ContentStatus;
import com.veritaschambers.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class DataSeeder {

    @Bean
    @Profile("!prod") // Never run this in production
    CommandLineRunner initMockData(
            PracticeAreaRepository practiceAreaRepository,
            LawyerProfileRepository lawyerProfileRepository,
            ArticleCategoryRepository articleCategoryRepository,
            ArticleRepository articleRepository,
            FaqRepository faqRepository) {
        
        return args -> {
            if (practiceAreaRepository.count() == 0) {
                createPracticeArea(practiceAreaRepository, "Civil Litigation", "civil-litigation", "Strategic counsel for complex civil disputes and property matters.");
                createPracticeArea(practiceAreaRepository, "Corporate Law", "corporate-law", "Comprehensive legal advisory for business formation, compliance, and disputes.");
                createPracticeArea(practiceAreaRepository, "Family Law", "family-law", "Discreet and compassionate representation for sensitive family matters.");
            }

            // Seed Lawyer Profile
            if (lawyerProfileRepository.count() == 0) {
                LawyerProfile profile = new LawyerProfile();
                profile.setName("Dhiraj Sawant");
                profile.setTitle("Advocate, High Court");
                profile.setBiography("Dhiraj Sawant is a highly respected advocate practicing in Sangli. This biography will be updated with full credentials, bar enrollment details, and professional experience.");
                profile.setCredentials("LL.B., B.A. | Bar Council of Maharashtra and Goa");
                lawyerProfileRepository.save(profile);
            }

            // Seed Articles
            if (articleCategoryRepository.count() == 0 && articleRepository.count() == 0) {
                ArticleCategory cat = new ArticleCategory();
                cat.setName("Legal Updates");
                cat.setSlug("legal-updates");
                cat.setDescription("Recent developments in Indian Law.");
                articleCategoryRepository.save(cat);

                Article article = new Article();
                article.setTitle("Understanding Property Disputes in Maharashtra");
                article.setSlug("understanding-property-disputes");
                article.setExcerpt("A brief overview of common property disputes and how strategic legal counsel can protect your assets.");
                article.setContent("<p>Property law involves complex regulations governing ownership, transfer, and disputes. The actual content of this article will be provided by Veritas Chambers. Do not rely on this text for legal advice.</p>");
                article.setCategory(cat);
                article.setStatus(ContentStatus.PUBLISHED);
                articleRepository.save(article);
            }

            // Seed FAQs
            if (faqRepository.count() == 0) {
                Faq faq1 = new Faq();
                faq1.setQuestion("Do you offer initial consultations?");
                faq1.setAnswer("Yes, we offer initial consultations. Please use the contact form to schedule an appointment at our Sangli office.");
                faq1.setDisplayOrder(1);
                faq1.setIsActive(true);
                faqRepository.save(faq1);

                Faq faq2 = new Faq();
                faq2.setQuestion("What are your office hours?");
                faq2.setAnswer("Meetings are strictly by prior appointment. Please contact us to schedule.");
                faq2.setDisplayOrder(2);
                faq2.setIsActive(true);
                faqRepository.save(faq2);
            }
        };
    }

    private void createPracticeArea(PracticeAreaRepository repo, String title, String slug, String excerpt) {
        PracticeArea pa = new PracticeArea();
        pa.setTitle(title);
        pa.setSlug(slug);
        pa.setShortDescription(excerpt);
        pa.setDescription("<p>Detailed information regarding " + title + " will be updated here.</p>");
        pa.setStatus(ContentStatus.PUBLISHED);
        repo.save(pa);
    }
}
