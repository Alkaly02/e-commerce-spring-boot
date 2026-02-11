package com.e_com.e_com_spring.mapper;

import com.e_com.e_com_spring.dto.product.ProductGetDto;
import com.e_com.e_com_spring.dto.product.ProductPostDto;
import com.e_com.e_com_spring.model.Category;
import com.e_com.e_com_spring.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public abstract class ProductMapper {

    @Mapping(source = "categoryId", target = "category", qualifiedByName = "mapCategory")
    public abstract Product toProduct(ProductPostDto postDto);

    @Named("mapCategory")
    Category mapCategory(Long id){
        if (id == null) return null;
        Category category = new Category();
        category.setId(id);
        return category;
    }

    @Mapping(source = "category", target = "category")
    public abstract ProductGetDto toGetDto(Product save);
}
