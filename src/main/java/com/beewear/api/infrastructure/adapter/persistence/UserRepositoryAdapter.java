package com.beewear.api.infrastructure.adapter.persistence;

import com.beewear.api.application.ports.outbound.persistence.UserRepositoryPort;
import com.beewear.api.domain.entities.User;
import com.beewear.api.domain.exceptions.InvalidCredentialsException;
import com.beewear.api.domain.exceptions.UserNotFoundException;
import com.beewear.api.infrastructure.adapter.persistence.mappers.UserJpaMapper;
import com.beewear.api.infrastructure.adapter.persistence.models.UserJpaModel;
import com.beewear.api.infrastructure.adapter.persistence.repositories.SpringUserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Slf4j
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
    public User update(User user) {
        UserJpaModel model = repository.findById(user.getId())
                .orElseThrow(UserNotFoundException::new);

        if(user.getDisplayName() != null) {
            model.setDisplayName(user.getDisplayName());
        }
        if(user.getPhoneNumber() != null) {
            model.setPhoneNumber(user.getPhoneNumber());
        }
        if(user.getBio() != null) {
            model.setBio(user.getBio());
        }
        if(user.getProfilePicture() != null) {
            model.setProfilePicture(user.getProfilePicture());
        }

        UserJpaModel updatedModel = repository.save(model);

        return userJpaMapper.toDomain(updatedModel);
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
