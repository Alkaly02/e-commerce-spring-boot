package com.e_com.e_com_spring.service.admin.user.checker;

import com.e_com.e_com_spring.model.User;

public interface IChecker {
    boolean canPerformAdminAction(User currentUser);
}
