package com.e_com.e_com_spring.service.admin.product;

import com.e_com.e_com_spring.dto.product.ProductGetDto;
import com.e_com.e_com_spring.dto.product.ProductPostDto;
import com.e_com.e_com_spring.model.User;

import java.awt.print.Pageable;
import java.util.List;

public interface IProductService {
    ProductGetDto create(ProductPostDto postDto, User currentUser);
    List<ProductGetDto> search(Pageable page);
    ProductGetDto getById(Long id);
    ProductGetDto update(Long id, ProductPostDto putDto, User currentUser);
    void delete(Long id, User currentUser);
}
