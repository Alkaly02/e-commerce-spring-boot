package com.e_com.e_com_spring.dto;

import lombok.Data;

@Data
public class AuditDto extends DateAuditDto {
    Long createdBy;
    Long updatedBy;
}
