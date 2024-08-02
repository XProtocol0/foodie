package com.github.foodie.services;

import com.github.foodie.controllers.request.RegisterUserReq;
import com.github.foodie.entities.UserAccountEntity;

public interface UserAccountService {
    void registerUser(RegisterUserReq registerUserReq);
    UserAccountEntity getUserById(Long userId);
}
