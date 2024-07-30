package com.github.foodie.services;

import com.github.foodie.dtos.ShipperDto;
import com.github.foodie.dtos.SignUpDto;
import com.github.foodie.dtos.UserDto;

public interface AuthService {
    String login(String email, String password);
    UserDto signUp(SignUpDto signUpDto);
    ShipperDto onboardShipper(Long userId);
}
