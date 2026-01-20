package com.e_com.e_com_spring.service.admin.user;

import com.e_com.e_com_spring.dto.user.UserMiniDto;
import com.e_com.e_com_spring.exception.CustomException;
import com.e_com.e_com_spring.mapper.user.UserMapper;
import com.e_com.e_com_spring.model.User;
import com.e_com.e_com_spring.repository.UserRepository;
import com.e_com.e_com_spring.service.admin.user.checker.IChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final IChecker checker;

//    @Override
//    public List<UserMiniDto> getAll(Pageable pageable) {
//        System.out.println("Hello");
//        return null;
//    }

    @Override
    public UserMiniDto enable(Long userId, User currentUser) {
        checker.canPerformAction(currentUser);
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()){
            throw new CustomException("User does not exist", HttpStatus.NOT_FOUND);
        }
        var user = optionalUser.get();
        user.setEnabled(true);
        return userMapper.toUserMiniDto(userRepository.save(user));
    }

    @Override
    public UserMiniDto disable(Long userId, User currentUser) {
        return null;
    }
}
