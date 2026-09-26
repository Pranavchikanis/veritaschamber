package com.veritaschambers.repository;

import com.veritaschambers.entity.LawyerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LawyerProfileRepository extends JpaRepository<LawyerProfile, Long> {
}
