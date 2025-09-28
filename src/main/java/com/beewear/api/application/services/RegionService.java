package com.beewear.api.application.services;

import com.beewear.api.application.ports.inbound.region.GetRegions;
import com.beewear.api.application.ports.outbound.persistence.RegionRepositoryPort;
import com.beewear.api.domain.entities.Region;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RegionService implements GetRegions {

    private RegionRepositoryPort regionRepository;

    @Override
    public List<Region> getRegions() {
        return regionRepository.getRegions();
    }
}
