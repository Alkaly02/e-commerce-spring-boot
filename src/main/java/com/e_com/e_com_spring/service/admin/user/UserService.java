package com.e_com.e_com_spring.service.admin.user;

import com.e_com.e_com_spring.dto.user.UserMiniDto;
import com.e_com.e_com_spring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{
    private final UserRepository userRepository;

//    @Override
//    public List<UserMiniDto> getAll(Pageable pageable) {
//        System.out.println("Hello");
//        return null;
//    }

    @Override
    public UserMiniDto enable(Long userId) {
        return null;
    }

    @Override
    public UserMiniDto disable(Long userId) {
        return null;
    }
}
