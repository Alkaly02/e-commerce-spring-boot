package com.e_com.e_com_spring.dto.category;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryGetDto {
    private Long id;
    private String name;
}
