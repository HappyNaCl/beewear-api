package com.beewear.api.application.ports.inbound.region;

import com.beewear.api.domain.entities.Region;

import java.util.List;

public interface GetRegions {
    List<Region> getRegions();
}
