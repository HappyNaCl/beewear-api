package com.beewear.api.infrastructure.adapter.persistence.seeder;

import com.beewear.api.infrastructure.adapter.persistence.models.RegionJpaModel;
import com.beewear.api.infrastructure.adapter.persistence.models.SubscriptionPlanJpaModel;
import com.beewear.api.infrastructure.adapter.persistence.repositories.SpringRegionRepository;
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
    private final SpringRegionRepository regionRepository;

    private void seedRegion() {
        if (regionRepository.count() == 0) {
            List<RegionJpaModel> regions = List.of(
                    new RegionJpaModel(null, "BINUS Kemanggisan"),
                    new RegionJpaModel(null, "BINUS Alam Sutera"),
                    new RegionJpaModel(null, "BINUS Bekasi"),
                    new RegionJpaModel(null, "BINUS Bandung"),
                    new RegionJpaModel(null, "BINUS Semarang"),
                    new RegionJpaModel(null, "BINUS Malang"),
                    new RegionJpaModel(null, "BINUS Medan")
            );

            regionRepository.saveAll(regions);
            log.info("Regions seeded!");
        }
    }

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
        seedRegion();
        seedSubscriptionPlan();
    }
}
