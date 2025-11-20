package com.beewear.api.infrastructure.adapter.persistence.mappers;

import com.beewear.api.domain.entities.User;
import com.beewear.api.infrastructure.adapter.persistence.models.UserJpaModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserJpaMapper {

    @Mapping(target = "subscriptions", ignore = true)
    @Mapping(target = "products", ignore = true)
    User toDomain(UserJpaModel model);

    @Mapping(target = "subscriptions", ignore = true)
    @Mapping(target = "products", ignore = true)
    UserJpaModel toJpaModel(User user);

}
