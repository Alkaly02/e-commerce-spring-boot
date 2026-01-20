package com.e_com.e_com_spring.service.admin.user;

import com.e_com.e_com_spring.dto.user.UserMiniDto;
import com.e_com.e_com_spring.dto.user.UserPostDto;
import com.e_com.e_com_spring.model.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IUserService {
//    List<UserMiniDto> getAll(Pageable pageable);
    // TODO: update
    UserMiniDto enable(Long userId, User user);
    UserMiniDto disable(Long userId, User user);
}
