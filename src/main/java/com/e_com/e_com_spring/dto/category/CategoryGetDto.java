package com.e_com.e_com_spring.dto.category;

import com.e_com.e_com_spring.dto.AuditDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryGetDto extends AuditDto {
    private Long id;
    private String name;
}
