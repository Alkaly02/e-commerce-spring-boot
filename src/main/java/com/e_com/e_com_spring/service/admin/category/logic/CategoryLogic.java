package com.e_com.e_com_spring.service.admin.category.logic;

import com.e_com.e_com_spring.dto.category.CategoryGetDto;
import com.e_com.e_com_spring.dto.category.CategoryPostDto;
import com.e_com.e_com_spring.exception.CustomException;
import com.e_com.e_com_spring.mapper.CategoryMapper;
import com.e_com.e_com_spring.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryLogic implements ICategoryLogic {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryGetDto update(Long id, CategoryPostDto putDto) {
        return categoryRepository.findById(id).map(category -> {
            category.setName(putDto.getName());
            return categoryMapper.toGetDto(categoryRepository.save(category));
        }).orElseThrow(() -> new CustomException("Category not found", HttpStatus.NOT_FOUND));
    }

    @Override
    public void delete(Long id) {
        categoryRepository.findById(id).ifPresentOrElse(
                categoryRepository::delete,
                () -> {
                    throw new CustomException("Category not found", HttpStatus.NOT_FOUND);
                }
        );
    }
}
