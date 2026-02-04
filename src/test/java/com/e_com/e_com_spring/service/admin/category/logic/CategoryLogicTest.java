package com.e_com.e_com_spring.service.admin.category.logic;

import com.e_com.e_com_spring.dto.category.CategoryGetDto;
import com.e_com.e_com_spring.dto.category.CategoryPostDto;
import com.e_com.e_com_spring.exception.CustomException;
import com.e_com.e_com_spring.mapper.CategoryMapper;
import com.e_com.e_com_spring.model.Category;
import com.e_com.e_com_spring.repository.CategoryRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CategoryLogicTest {
    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryLogic categoryLogic;

    @Nested
    class UpdateTests{
        @Test
        void shouldUpdate_WhenCategoryIsFound(){
            // Given
            Long categoryId  = 1L;
            Category oldCategory = createCategory(1L, "Cat 1");
            Category updatedCategory = createCategory(1L, "New Cat 1");
            CategoryPostDto postDto = CategoryPostDto.builder()
                    .name("New Cat 1")
                    .build();
            CategoryGetDto categoryGetDto = CategoryGetDto.builder()
                    .id(1L)
                    .name("New Cat 1")
                    .build();
            oldCategory.setName("Cat 1");
            when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(oldCategory));
            when(categoryRepository.save(any(Category.class))).thenReturn(updatedCategory);
            when(categoryMapper.toGetDto(updatedCategory)).thenReturn(categoryGetDto);

            // When
            CategoryGetDto actual = categoryLogic.update(categoryId, postDto);

            // Then
            assertNotNull(actual);
            assertEquals(postDto.getName(), updatedCategory.getName());

            verify(categoryRepository, times(1)).findById(any(Long.class));
            verify(categoryRepository, times(1)).save(any(Category.class));
            verify(categoryMapper, times(1)).toGetDto(any(Category.class));
        }

        @Test
        void shouldNotUpdate_WhenCategoryNotFound(){
            // Given
            Long notFoundCategoryId = 999L;
            when(categoryRepository.findById(notFoundCategoryId)).thenReturn(Optional.empty());

            // When
            CustomException exception = assertThrows(CustomException.class, () -> {
                categoryLogic.update(notFoundCategoryId, null);
            });

            // Then
            assertEquals(exception.getMessage(), "Category not found");
            assertEquals(exception.getStatus(), HttpStatus.NOT_FOUND);

            verify(categoryRepository, times(1)).findById(any(Long.class));
            verify(categoryRepository, times(0)).save(any(Category.class));
            verify(categoryMapper, times(0)).toGetDto(any(Category.class));
        }
    }

    private Category createCategory(Long id, String name){
        Category newCategory = new Category();
        newCategory.setId(id);
        newCategory.setName(name);
        return newCategory;
    }
}