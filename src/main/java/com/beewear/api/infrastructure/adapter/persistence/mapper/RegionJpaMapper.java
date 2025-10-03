package com.beewear.api.infrastructure.adapter.persistence.mapper;

import com.beewear.api.domain.entities.Region;
import com.beewear.api.infrastructure.adapter.persistence.models.RegionJpaModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {RegionJpaMapper.class})
public interface RegionJpaMapper {

    RegionJpaModel toJpaModel(Region region);

    Region toDomain(RegionJpaModel jpaModel);

}
