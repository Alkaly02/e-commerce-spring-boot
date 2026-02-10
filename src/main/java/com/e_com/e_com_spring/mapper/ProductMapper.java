package com.e_com.e_com_spring.mapper;

import com.e_com.e_com_spring.dto.product.ProductPostDto;
import com.e_com.e_com_spring.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping()
    Product toProduct(ProductPostDto postDto);
}
