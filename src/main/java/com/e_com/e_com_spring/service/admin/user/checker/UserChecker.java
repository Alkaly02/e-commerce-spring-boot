package com.e_com.e_com_spring.service.admin.user.checker;

import com.e_com.e_com_spring.exception.CustomException;
import com.e_com.e_com_spring.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class UserChecker implements IChecker{
    @Override
    public boolean canPerformAdminAction(User currentUser) {
        if (!currentUser.isAdmin()){
            throw new CustomException("You are not allowed to perform this action", HttpStatus.FORBIDDEN);
        }
        return true;
    }
}
