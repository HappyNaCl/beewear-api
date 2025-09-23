package com.beewear.api.infrastructure.adapter.outbound.persistence.mapper;

import com.beewear.api.domain.entities.User;
import com.beewear.api.infrastructure.adapter.outbound.persistence.models.UserJpaModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserJpaMapper {

    User toDomain(UserJpaModel model);

    UserJpaModel toJpaModel(User user);

}
