package com.e_com.e_com_spring.service.admin.category;

import com.e_com.e_com_spring.dto.category.CategoryGetDto;
import com.e_com.e_com_spring.dto.category.CategoryPostDto;
import com.e_com.e_com_spring.exception.CustomException;
import com.e_com.e_com_spring.mapper.CategoryMapper;
import com.e_com.e_com_spring.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryGetDto create(CategoryPostDto postDto) {
        return categoryMapper.toGetDto(categoryRepository.save(categoryMapper.toCategory(postDto)));
    }

    @Override
    public List<CategoryGetDto> getAll() {
        return categoryRepository.findAll().stream().map(categoryMapper::toGetDto).toList();
    }

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
