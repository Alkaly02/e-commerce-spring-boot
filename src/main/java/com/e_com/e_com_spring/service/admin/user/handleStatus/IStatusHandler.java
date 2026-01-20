package com.e_com.e_com_spring.service.admin.user.handleStatus;

import com.e_com.e_com_spring.model.User;

public interface IStatusHandler {
    User handleStatus(Long userId, boolean enable);
}
