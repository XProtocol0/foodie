package com.github.foodie.controllers.request;

import lombok.Data;

@Data
public class RegisterUserReq {
    private String name;
    private String email;
    private String password;
    private String role;
}
