package com.github.foodie.controllers;

import com.github.foodie.controllers.request.RegisterUserReq;
import com.github.foodie.controllers.response.GenericResponse;
import com.github.foodie.services.UserAccountService;
import net.logstash.logback.marker.LogstashMarker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static net.logstash.logback.marker.Markers.append;

@RestController
@RequestMapping("user")
public class UserAccountController {

    @Autowired
    private UserAccountService userAccountService;

    @PostMapping("/register")
    public ResponseEntity<GenericResponse<String>> registerUser(@RequestBody RegisterUserReq registerUserReq) {
        LogstashMarker marker = append("method", "registerUser");
        userAccountService.registerUser(registerUserReq);
        return ResponseEntity.ok(
                new GenericResponse<>(true, "User successfully registered", null));
    }
}
