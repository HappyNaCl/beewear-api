package com.beewear.api.application.services;

import com.beewear.api.application.ports.inbound.subscription.UserSubscribeUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SubscriptionService implements UserSubscribeUseCase {



    @Override
    public void subscribeUser() {

    }
}
