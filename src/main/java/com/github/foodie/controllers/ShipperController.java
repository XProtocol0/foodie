package com.github.foodie.controllers;

import com.github.foodie.controllers.request.RegisterShipperReq;
import com.github.foodie.services.ShipperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shipper")
public class ShipperController {

    @Autowired
    private ShipperService shipperService;

    @PostMapping("/register")
    public ResponseEntity<String> registerShipper(@RequestBody RegisterShipperReq registerShipperReq) {
        shipperService.registerShipper(registerShipperReq);
        return ResponseEntity.ok("Shipper registered successfully");
    }
}
