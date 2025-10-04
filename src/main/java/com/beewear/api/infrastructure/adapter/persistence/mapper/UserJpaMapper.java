package com.beewear.api.infrastructure.adapter.persistence.mapper;

import com.beewear.api.domain.entities.User;
import com.beewear.api.infrastructure.adapter.persistence.models.UserJpaModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserJpaMapper {

    @Mapping(target = "regionId", source = "region.id")
    User toDomain(UserJpaModel model);

    @Mapping(target = "region.id", source = "regionId")
    UserJpaModel toJpaModel(User user);

}
