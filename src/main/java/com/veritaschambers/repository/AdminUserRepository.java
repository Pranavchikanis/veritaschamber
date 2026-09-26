package com.veritaschambers.repository;

import com.veritaschambers.entity.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminUserRepository extends JpaRepository<AdminUser, Long> {
    java.util.Optional<AdminUser> findByEmail(String email);
}
