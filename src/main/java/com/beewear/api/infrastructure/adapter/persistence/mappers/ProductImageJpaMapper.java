package com.beewear.api.infrastructure.adapter.persistence.mappers;

import com.beewear.api.domain.entities.ProductImage;
import com.beewear.api.infrastructure.adapter.persistence.models.ProductImageJpaModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductImageJpaMapper {

    @Mapping(target = "product", ignore = true)
    ProductImage toDomain(ProductImageJpaModel jpaModel);

    @Mapping(target = "product", ignore = true)
    ProductImageJpaModel toJpaModel(ProductImage productImage);
}
