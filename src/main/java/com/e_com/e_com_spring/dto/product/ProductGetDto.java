package com.e_com.e_com_spring.dto.product;

import com.e_com.e_com_spring.dto.category.CategoryGetDto;
import lombok.Data;

@Data
public class ProductGetDto {
    Long id;
    String name;
    CategoryGetDto category;
}
