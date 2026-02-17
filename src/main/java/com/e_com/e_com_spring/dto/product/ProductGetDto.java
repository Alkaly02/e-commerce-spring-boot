package com.e_com.e_com_spring.dto.product;

import com.e_com.e_com_spring.dto.category.CategoryGetDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductGetDto {
    Long id;
    String name;
    CategoryGetDto category;
}
