package com.e_com.e_com_spring.service.admin.user.handleStatus;

import com.e_com.e_com_spring.exception.CustomException;
import com.e_com.e_com_spring.model.User;
import com.e_com.e_com_spring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class StatusHandler implements IStatusHandler{
    private final UserRepository userRepository;

    @Override
    public User handleStatus(Long userId, boolean enable) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()){
            throw new CustomException("User does not exist", HttpStatus.NOT_FOUND);
        }
        var user = optionalUser.get();
        user.setEnabled(enable);
        return userRepository.save(user);
    }
}
