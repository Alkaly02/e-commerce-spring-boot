package com.e_com.e_com_spring.util;

import com.e_com.e_com_spring.dto.category.CategoryGetDto;
import com.e_com.e_com_spring.dto.category.CategoryPostDto;
import com.e_com.e_com_spring.mapper.CategoryMapper;
import com.e_com.e_com_spring.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryUtils {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryGetDto createGetDto(String categoryName){
        return categoryMapper.toGetDto(
                categoryRepository.save(
                        categoryMapper.toCategory(
                                CategoryPostDto
                                        .builder()
                                        .name(categoryName)
                                        .build()
                        )
                )
        );
    }
}
