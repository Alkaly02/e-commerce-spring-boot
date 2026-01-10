package com.e_com.e_com_spring.service.auth.createUser;

import com.e_com.e_com_spring.dto.auth.RegisterPostDto;
import com.e_com.e_com_spring.model.User;

public interface ICreateUserService {
    User create(RegisterPostDto postDto);
}
