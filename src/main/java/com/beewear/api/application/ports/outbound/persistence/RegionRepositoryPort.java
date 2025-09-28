package com.beewear.api.application.ports.outbound.persistence;

import com.beewear.api.domain.entities.Region;

import java.util.List;

public interface RegionRepositoryPort {
    List<Region> getRegions();
}
