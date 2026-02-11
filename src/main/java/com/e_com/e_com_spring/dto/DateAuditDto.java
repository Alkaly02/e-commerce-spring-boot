package com.e_com.e_com_spring.dto;

import lombok.Data;

import java.util.Date;

@Data
public class DateAuditDto {
    private Date createdAt;
    private Date updatedAt;
}
