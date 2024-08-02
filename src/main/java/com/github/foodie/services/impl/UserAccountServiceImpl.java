package com.github.foodie.services.impl;

import com.github.foodie.constants.Role;
import com.github.foodie.controllers.request.RegisterUserReq;
import com.github.foodie.entities.UserAccountEntity;
import com.github.foodie.repositories.UserAccountRepository;
import com.github.foodie.services.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Service
public class UserAccountServiceImpl implements UserAccountService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Override
    public void registerUser(RegisterUserReq registerUserReq) {
        UserAccountEntity userAccountEntity = new UserAccountEntity();
        Set<Role> roles  = new HashSet<>();
        roles.add(Role.valueOf(registerUserReq.getRole()));

        userAccountEntity.setName(registerUserReq.getName());
        userAccountEntity.setEmail(registerUserReq.getEmail());
        userAccountEntity.setPassword(registerUserReq.getPassword());
        userAccountEntity.setRoles(roles);
        userAccountRepository.save(userAccountEntity);
    }

    @Override
    public UserAccountEntity getUserById(Long userId) {
        UserAccountEntity userAccountEntity = userAccountRepository.findById(userId).orElse(null);
        if(Objects.isNull(userAccountEntity)) {
            //error
        }
        return userAccountEntity;
    }
}
