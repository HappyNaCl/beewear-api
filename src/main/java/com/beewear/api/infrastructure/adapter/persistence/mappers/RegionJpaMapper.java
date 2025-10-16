package com.beewear.api.infrastructure.adapter.persistence.mappers;

import com.beewear.api.domain.entities.Region;
import com.beewear.api.infrastructure.adapter.persistence.models.RegionJpaModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RegionJpaMapper {

    @Mapping(target = "users" , ignore = true)
    RegionJpaModel toJpaModel(Region region);

    @Mapping(target = "users" , ignore = true)
    Region toDomain(RegionJpaModel jpaModel);

}
