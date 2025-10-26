package com.beewear.api.infrastructure.adapter.persistence.seeder;

import com.beewear.api.infrastructure.adapter.persistence.models.SubscriptionPlanJpaModel;
import com.beewear.api.infrastructure.adapter.persistence.repositories.SpringSubscriptionPlanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DatabaseSeeder implements CommandLineRunner {

    private final SpringSubscriptionPlanRepository subscriptionPlanRepository;

    private void seedSubscriptionPlan() {
        if(subscriptionPlanRepository.count() == 0) {
            List<SubscriptionPlanJpaModel> plans = List.of(
                    new SubscriptionPlanJpaModel(
                            null,
                            "BeeWearer",
                            "https://res.cloudinary.com/dq8ung8v9/image/upload/v1761017749/rewearer_jsk46l.png",
                            "Up to 5 active listings at a time.",
                            10000.0
                    ),
                    new SubscriptionPlanJpaModel(
                            null,
                            "BeeWearer Plus",
                            "https://res.cloudinary.com/dq8ung8v9/image/upload/v1761017750/rewearer-plus_jczbfq.png",
                            "Up to 7 active listings at a time and boosted listings.",
                            25000.0
                    )
            );

            subscriptionPlanRepository.saveAll(plans);
            log.info("Subscription plans seeded!");
        }
    }

    @Override
    public void run(String... args) throws Exception {
        seedSubscriptionPlan();
    }
}
