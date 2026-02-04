package com.e_com.e_com_spring.service.admin.category.logic;

import com.e_com.e_com_spring.dto.category.CategoryGetDto;
import com.e_com.e_com_spring.dto.category.CategoryPostDto;

public interface ICategoryLogic {
    CategoryGetDto update(Long id, CategoryPostDto putDto);
    void delete(Long id);
}
