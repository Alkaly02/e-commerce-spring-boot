package com.e_com.e_com_spring.service.admin.product;

import com.e_com.e_com_spring.dto.product.ProductGetDto;
import com.e_com.e_com_spring.dto.product.ProductPostDto;
import com.e_com.e_com_spring.exception.CustomException;
import com.e_com.e_com_spring.mapper.ProductMapper;
import com.e_com.e_com_spring.model.Category;
import com.e_com.e_com_spring.model.Product;
import com.e_com.e_com_spring.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
        Product newProduct = productMapper.toProduct(postDto);
        return productMapper.toGetDto(productRepository.save(newProduct));
    }

    @Override
    public List<ProductGetDto> search(Pageable page) {
        return List.of();
    }

    @Override
    public ProductGetDto getById(Long id) {
        // TODO: Check if hibernate handle null id
        return productRepository.findById(id)
                .map(productMapper::toGetDto)
                .orElseThrow(() -> new CustomException("Product not found", HttpStatus.NOT_FOUND));
    }

    @Override
    public ProductGetDto update(Long id, ProductPostDto putDto) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setName(putDto.getName());
                    Category category = new Category();
                    category.setId(putDto.getCategoryId());
                    product.setCategory(category);
                    return productMapper.toGetDto(product);
                })
                .orElseThrow(() -> new CustomException("Product not found", HttpStatus.NOT_FOUND));
    }

    @Override
    public void delete(Long id) {
        productRepository.findById(id)
                .ifPresentOrElse(productRepository::delete,
                        () -> {
                            throw new CustomException("Product not found", HttpStatus.NOT_FOUND);
                        }
                        );
    }
}
