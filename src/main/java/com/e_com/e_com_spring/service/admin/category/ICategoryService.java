package com.e_com.e_com_spring.service.admin.category;

import com.e_com.e_com_spring.dto.category.CategoryGetDto;
import com.e_com.e_com_spring.dto.category.CategoryPostDto;

import java.util.List;

public interface ICategoryService {
    CategoryGetDto create(CategoryPostDto postDto);
    List<CategoryGetDto> getAll();
    CategoryGetDto update(Long id, CategoryPostDto putDto);
    void delete(Long id);
}
