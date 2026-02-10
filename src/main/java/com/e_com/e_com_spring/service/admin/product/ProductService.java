package com.e_com.e_com_spring.service.admin.product;

import com.e_com.e_com_spring.dto.product.ProductGetDto;
import com.e_com.e_com_spring.dto.product.ProductPostDto;
import com.e_com.e_com_spring.mapper.ProductMapper;
import com.e_com.e_com_spring.model.Product;
import com.e_com.e_com_spring.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService{
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductGetDto create(ProductPostDto postDto) {
        Product newProduct =
        return null;
    }

    @Override
    public List<ProductGetDto> search(Pageable page) {
        return List.of();
    }

    @Override
    public ProductGetDto getById(Long id) {
        return null;
    }

    @Override
    public ProductGetDto update(Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
