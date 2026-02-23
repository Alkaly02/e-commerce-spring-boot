package com.e_com.e_com_spring.dto.product;

import com.e_com.e_com_spring.dto.category.CategoryGetDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductGetDto {
    Long id;
    String name;
    CategoryGetDto category;
}
