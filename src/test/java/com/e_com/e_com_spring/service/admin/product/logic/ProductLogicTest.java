package com.e_com.e_com_spring.service.admin.product.logic;

import com.e_com.e_com_spring.dto.category.CategoryGetDto;
import com.e_com.e_com_spring.dto.product.ProductGetDto;
import com.e_com.e_com_spring.dto.product.ProductPostDto;
import com.e_com.e_com_spring.mapper.ProductMapper;
import com.e_com.e_com_spring.model.Category;
import com.e_com.e_com_spring.model.Product;
import com.e_com.e_com_spring.repository.ProductRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductLogicTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductLogic productLogic;

    @Nested
    class CreateTests{
        @Test
        void shouldCreate(){
            // Arrange
            ProductPostDto postDto = createPostDto("Product 1", 1L);
            ProductGetDto productGetDto = createGetDto(1L, "Product 1", 1L);
            Product newProduct = createProduct(null, "Product 1", 1L);
            Product savedProduct = createProduct(1L, "Product 1", 1L);

            when(productMapper.toProduct(postDto)).thenReturn(newProduct);
            when(productRepository.save(newProduct)).thenReturn(savedProduct);
            when(productMapper.toGetDto(savedProduct)).thenReturn(productGetDto);

            // Act
            ProductGetDto actual = productLogic.create(postDto);

            ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);

            // Assert
            assertNotNull(actual);
            assertEquals(actual.getName(), postDto.getName());
            assertEquals(actual.getCategory().getId(), postDto.getCategoryId());

            verify(productRepository, times(1)).save(productCaptor.capture());
            verify(productMapper, times(1)).toGetDto(any(Product.class));

            Product capturedProduct = productCaptor.getValue();
            assertEquals(postDto.getName(), capturedProduct.getName());
            assertEquals(postDto.getCategoryId(), capturedProduct.getCategory().getId());
        }
    }

    private ProductPostDto createPostDto(String name, Long catId) {
        return ProductPostDto.builder().name(name).categoryId(catId).build();
    }

    private ProductGetDto createGetDto(Long id, String productName, Long catId){
        return ProductGetDto.builder()
                .id(id)
                .name(productName)
                .category(CategoryGetDto.builder().id(catId).build())
                .build();
    }

    private Product createProduct(Long id, String name, Long catId) {
        Category cat = new Category(); cat.setId(catId);
        Product p = new Product();
        p.setId(id); p.setName(name); p.setCategory(cat);
        return p;
    }
}