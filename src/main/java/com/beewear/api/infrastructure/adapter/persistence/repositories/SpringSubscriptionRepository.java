package com.beewear.api.infrastructure.adapter.persistence.repositories;

import com.beewear.api.domain.entities.Subscription;
import com.beewear.api.domain.entities.User;
import com.beewear.api.infrastructure.adapter.persistence.models.SubscriptionJpaModel;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface SpringSubscriptionRepository extends JpaRepository<SubscriptionJpaModel, UUID> {

    @EntityGraph(attributePaths = {"subscriptionPlan", "user"})
    @Query("SELECT s FROM SubscriptionJpaModel s WHERE s.user.id = :userId AND s.subscriptionStatus = 'ACTIVE'")
    Optional<SubscriptionJpaModel> findActiveByUserId(@Param("userId") UUID userId);

    UUID user(User user);
}
