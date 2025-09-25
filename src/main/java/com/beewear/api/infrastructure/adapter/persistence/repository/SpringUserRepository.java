package com.beewear.api.infrastructure.adapter.persistence.repository;

import com.beewear.api.infrastructure.adapter.persistence.models.UserJpaModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringUserRepository extends JpaRepository<UserJpaModel, UUID> {
    Optional<UserJpaModel> findByEmail(String email);

    boolean existsByEmail(String email);
}