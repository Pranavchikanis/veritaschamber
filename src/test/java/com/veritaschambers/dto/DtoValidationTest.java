package com.veritaschambers.dto;

import com.veritaschambers.dto.request.ArticleCreateRequest;
import com.veritaschambers.dto.request.ConsultationCreateRequest;
import com.veritaschambers.dto.request.ContactMessageCreateRequest;
import com.veritaschambers.entity.enums.ContentStatus;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DtoValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void consultationCreateRequest_Valid_ShouldPass() {
        ConsultationCreateRequest request = new ConsultationCreateRequest("John Doe", "john@example.com", "1234567890", "PHONE", "Subject", "Description", null, null);
        Set<ConstraintViolation<ConsultationCreateRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }

    @Test
    void consultationCreateRequest_InvalidEmail_ShouldFail() {
        ConsultationCreateRequest request = new ConsultationCreateRequest("John Doe", "invalid-email", "1234567890", "PHONE", "Subject", "Description", null, null);
        Set<ConstraintViolation<ConsultationCreateRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
    }

    @Test
    void contactMessageRequest_BlankFields_ShouldFail() {
        ContactMessageCreateRequest request = new ContactMessageCreateRequest("", "", "", "", "");
        Set<ConstraintViolation<ContactMessageCreateRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        // Expected multiple violations: Name, Email, Subject, Message, etc.
        assertTrue(violations.size() >= 3); // Name, Email, Message
    }

    @Test
    void articleCreateRequest_InvalidSlug_ShouldFail() {
        ArticleCreateRequest request = new ArticleCreateRequest(1L, "Title", "Invalid Slug With Spaces!", "Excerpt", "Content", null, ContentStatus.PUBLISHED);
        Set<ConstraintViolation<ArticleCreateRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("slug")));
    }

    @Test
    void articleCreateRequest_Valid_ShouldPass() {
        ArticleCreateRequest request = new ArticleCreateRequest(1L, "Title", "valid-slug-123", "Excerpt", "Content", null, ContentStatus.PUBLISHED);
        Set<ConstraintViolation<ArticleCreateRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }
}
