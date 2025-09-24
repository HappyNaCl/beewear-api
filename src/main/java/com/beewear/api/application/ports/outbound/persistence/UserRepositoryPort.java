package com.beewear.api.application.ports.outbound.persistence;

import com.beewear.api.domain.entities.User;

import java.util.UUID;

public interface UserRepositoryPort {

    User findByEmail(String email);
    User findById(UUID id);

    boolean existsByEmail(String email);

    User save(User user);
}
