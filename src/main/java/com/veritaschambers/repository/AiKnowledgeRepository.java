package com.veritaschambers.repository;

import com.veritaschambers.entity.AiKnowledgeRecord;
import com.veritaschambers.entity.enums.VerificationStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AiKnowledgeRepository extends JpaRepository<AiKnowledgeRecord, Long> {

    @Query("SELECT r FROM AiKnowledgeRecord r " +
           "WHERE r.verificationStatus = :verificationStatus " +
           "AND r.isActive = true " +
           "AND (LOWER(r.titleKey) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "  OR LOWER(r.content) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "  OR LOWER(r.category) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<AiKnowledgeRecord> findRelevantKnowledge(
            @Param("keyword") String keyword,
            @Param("verificationStatus") VerificationStatus verificationStatus,
            Pageable pageable
    );
}
