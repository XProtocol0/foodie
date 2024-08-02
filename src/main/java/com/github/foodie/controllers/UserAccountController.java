package com.github.foodie.controllers;

import com.github.foodie.controllers.request.RegisterUserReq;
import com.github.foodie.services.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserAccountController {

    @Autowired
    private UserAccountService userAccountService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterUserReq registerUserReq) {
        userAccountService.registerUser(registerUserReq);
        return ResponseEntity.ok("User successfully registered");
    }
}
