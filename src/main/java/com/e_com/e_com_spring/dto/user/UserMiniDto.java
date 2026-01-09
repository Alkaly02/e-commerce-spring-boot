package com.e_com.e_com_spring.dto.user;

import lombok.Data;

@Data
public class UserMiniDto {
    private Long id;
    private String firstName;
    private String lastName;
    private boolean enabled;
}
