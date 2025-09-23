package com.beewear.api.application.ports.outbound.persistence;

import com.beewear.api.domain.entities.User;

public interface UserRepositoryPort {

    User findByEmail(String email);

    boolean existsByEmail(String email);

    User save(User user);
}
