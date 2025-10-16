package com.beewear.api.infrastructure.adapter.persistence;

import com.beewear.api.application.ports.outbound.persistence.RegionRepositoryPort;
import com.beewear.api.domain.entities.Region;
import com.beewear.api.infrastructure.adapter.persistence.mappers.RegionJpaMapper;
import com.beewear.api.infrastructure.adapter.persistence.models.RegionJpaModel;
import com.beewear.api.infrastructure.adapter.persistence.repositories.SpringRegionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Repository
public class RegionRepositoryAdapter implements RegionRepositoryPort {

    private RegionJpaMapper regionJpaMapper;
    private SpringRegionRepository repository;

    @Override
    public List<Region> getRegions() {
        List<RegionJpaModel> regionModels = repository.findAll();

        List<Region> regions = new ArrayList<>();
        for (RegionJpaModel regionModel : regionModels) {
            regions.add(regionJpaMapper.toDomain(regionModel));
        }

        return regions;
    }
}
