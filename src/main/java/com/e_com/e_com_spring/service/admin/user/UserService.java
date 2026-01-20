package com.e_com.e_com_spring.service.admin.user;

import com.e_com.e_com_spring.dto.user.UserMiniDto;
import com.e_com.e_com_spring.mapper.user.UserMapper;
import com.e_com.e_com_spring.model.User;
import com.e_com.e_com_spring.service.admin.user.checker.IChecker;
import com.e_com.e_com_spring.service.admin.user.handleStatus.IStatusHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{
    private final UserMapper userMapper;
    private final IChecker checker;
    private final IStatusHandler statusHandler;

    @Override
    public UserMiniDto enable(Long userId, User currentUser) {
        checker.canPerformAdminAction(currentUser);
        User user = statusHandler.handleStatus(userId, true);
        return userMapper.toUserMiniDto(user);
    }

    @Override
    public UserMiniDto disable(Long userId, User currentUser) {
        checker.canPerformAdminAction(currentUser);
        User user = statusHandler.handleStatus(userId, false);
        return userMapper.toUserMiniDto(user);
    }
}
