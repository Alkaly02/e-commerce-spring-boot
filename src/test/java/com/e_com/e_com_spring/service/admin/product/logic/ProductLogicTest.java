package com.e_com.e_com_spring.service.admin.product.logic;

import com.e_com.e_com_spring.mapper.ProductMapper;
import com.e_com.e_com_spring.repository.ProductRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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

        }
    }
}