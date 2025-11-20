package com.beewear.api.application.services;

import com.beewear.api.application.ports.inbound.me.GetMeUseCase;
import com.beewear.api.application.ports.outbound.persistence.SubscriptionRepositoryPort;
import com.beewear.api.application.ports.outbound.persistence.UserRepositoryPort;
import com.beewear.api.application.services.dto.MeDto;
import com.beewear.api.domain.entities.Subscription;
import com.beewear.api.domain.entities.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MeService implements GetMeUseCase {

    private final SubscriptionRepositoryPort subscriptionRepository;
    private final UserRepositoryPort userRepositoryPort;

    @Override
    @Transactional
    public MeDto getMe(UUID userId) {
        User user = userRepositoryPort.findById(userId);
        Subscription subscription = subscriptionRepository.findActiveSubscriptionByUserId(userId);

        return new MeDto(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getProfilePicture(),
                subscription.getSubscriptionPlan() != null ?
                        subscription.getSubscriptionPlan().getName() : null
        );
    }

}
