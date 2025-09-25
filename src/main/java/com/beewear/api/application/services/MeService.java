package com.beewear.api.application.services;

import com.beewear.api.application.ports.inbound.me.GetMeUseCase;
import com.beewear.api.application.ports.outbound.persistence.UserRepositoryPort;
import com.beewear.api.application.services.dto.MeResult;
import com.beewear.api.domain.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MeService implements GetMeUseCase {

    private final UserRepositoryPort userRepositoryPort;

    @Override
    public MeResult getMe(UUID userId) {
        User user = userRepositoryPort.findById(userId);
        return new MeResult(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getProfilePicture()
        );
    }

}
