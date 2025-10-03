package com.beewear.api.infrastructure.adapter.persistence.seeder;

import com.beewear.api.infrastructure.adapter.persistence.models.RegionJpaModel;
import com.beewear.api.infrastructure.adapter.persistence.repository.SpringRegionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DatabaseSeeder implements CommandLineRunner {

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


    @Override
    public void run(String... args) throws Exception {
        seedRegion();
    }
}
