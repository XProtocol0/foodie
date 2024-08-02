package com.github.foodie.controllers;

import com.github.foodie.services.CustomerService;
import net.logstash.logback.marker.LogstashMarker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static net.logstash.logback.marker.Markers.append;


@RestController
@RequestMapping("customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<String> registerCustomer(@RequestParam Long userId) {
        LogstashMarker marker = append("method", "registerCustomer");
        customerService.registerCustomer(userId);
        return ResponseEntity.ok("Customer successfully registered");
    }
}
