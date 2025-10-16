package com.beewear.api.infrastructure.adapter.persistence.mappers;

import com.beewear.api.domain.entities.Product;
import com.beewear.api.infrastructure.adapter.persistence.models.ProductJpaModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ProductImageJpaMapper.class})
public interface ProductJpaMapper {

    @Mapping(target = "creatorId", source = "creator.id")
    @Mapping(target = "creator" , ignore = true)
    Product toDomain(ProductJpaModel jpaModel);

    @Mapping(target = "creator.id", source = "creatorId")
    @Mapping(target = "creator" , ignore = true)
    ProductJpaModel toJpaModel(Product product);

}
