package com.github.foodie.controllers.request;

import com.github.foodie.constants.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import validation.EnumValue;

@Data
public class RegisterUserReq {
    @NotBlank
    @NotNull
    private String name;
    @NotBlank
    @NotNull
    private String email;
    @NotBlank
    @NotNull
    private String password;
    @EnumValue(enumClass = Role.class, message = "Invalid role")
    @NotNull
    private String role;
}
