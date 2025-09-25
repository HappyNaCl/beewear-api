package com.beewear.api.infrastructure.adapter.persistence;

import com.beewear.api.application.ports.outbound.persistence.UserRepositoryPort;
import com.beewear.api.domain.entities.User;
import com.beewear.api.domain.exceptions.InvalidCredentialsException;
import com.beewear.api.domain.exceptions.UserNotFoundException;
import com.beewear.api.infrastructure.adapter.persistence.mapper.UserJpaMapper;
import com.beewear.api.infrastructure.adapter.persistence.models.UserJpaModel;
import com.beewear.api.infrastructure.adapter.persistence.repository.SpringUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@AllArgsConstructor
@Repository
public class UserRepositoryAdapter implements UserRepositoryPort {

    private UserJpaMapper userJpaMapper;
    private SpringUserRepository repository;

    @Override
    public User findByEmail(String email) {
        UserJpaModel model = repository
                .findByEmail(email)
                .orElseThrow(InvalidCredentialsException::new);
        return userJpaMapper.toDomain(model);
    }

    @Override
    public User findById(UUID id) {
        UserJpaModel model = repository
                .findById(id)
                .orElseThrow(UserNotFoundException::new);
        return userJpaMapper.toDomain(model);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public User save(User user) {
        UserJpaModel model = userJpaMapper.toJpaModel(user);
        UserJpaModel savedModel = repository.save(model);
        return userJpaMapper.toDomain(savedModel);
    }
}
