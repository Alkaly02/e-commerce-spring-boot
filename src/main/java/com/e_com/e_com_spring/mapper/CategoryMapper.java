package com.e_com.e_com_spring.mapper;

import com.e_com.e_com_spring.dto.category.CategoryGetDto;
import com.e_com.e_com_spring.dto.category.CategoryPostDto;
import com.e_com.e_com_spring.model.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryGetDto toGetDto(Category category);
    Category toCategory(CategoryPostDto postDto);
}
