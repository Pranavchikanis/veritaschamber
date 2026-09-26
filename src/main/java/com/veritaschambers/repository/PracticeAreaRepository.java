package com.veritaschambers.repository;

import com.veritaschambers.entity.PracticeArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PracticeAreaRepository extends JpaRepository<PracticeArea, Long> {
    Optional<PracticeArea> findBySlug(String slug);
}
