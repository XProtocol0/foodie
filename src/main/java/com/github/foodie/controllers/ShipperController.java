package com.github.foodie.controllers;

import com.github.foodie.controllers.request.RegisterShipperReq;
import com.github.foodie.controllers.response.GenericResponse;
import com.github.foodie.services.ShipperService;
import net.logstash.logback.marker.LogstashMarker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static net.logstash.logback.marker.Markers.append;

@RestController
@RequestMapping("/shipper")
public class ShipperController {

    @Autowired
    private ShipperService shipperService;

    @PostMapping("/register")
    public ResponseEntity<GenericResponse<String>> registerShipper(@RequestBody RegisterShipperReq registerShipperReq) {
        LogstashMarker marker = append("method", "registerShipper");
        shipperService.registerShipper(registerShipperReq);
        return ResponseEntity.ok(
                new GenericResponse<>(true, "Shipper registered successfully", null));
    }
}
