package com.e_com.e_com_spring.service.admin.category;

import com.e_com.e_com_spring.dto.category.CategoryGetDto;
import com.e_com.e_com_spring.dto.category.CategoryPostDto;
import com.e_com.e_com_spring.exception.CustomException;
import com.e_com.e_com_spring.mapper.CategoryMapper;
import com.e_com.e_com_spring.repository.CategoryRepository;
import com.e_com.e_com_spring.service.admin.category.logic.ICategoryLogic;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final ICategoryLogic categoryLogic;

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
        return categoryLogic.update(id, putDto);
    }

    @Override
    public void delete(Long id) {
        categoryLogic.delete(id);
    }
}
