package com.e_com.e_com_spring.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductPostDto {
    @NotBlank(message = "Product name must be given")
    String name;

    @NotNull(message = "Product categoryId must be given")
    Long categoryId;
}
