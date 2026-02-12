package com.e_com.e_com_spring.service.admin.product.logic;

import com.e_com.e_com_spring.dto.product.ProductGetDto;
import com.e_com.e_com_spring.dto.product.ProductPostDto;

public interface IProductLogic {
    ProductGetDto create(ProductPostDto postDto);
    ProductGetDto getById(Long id);
    ProductGetDto update(Long id, ProductPostDto putDto);
    void delete(Long id);
}
