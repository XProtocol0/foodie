package com.github.foodie.controllers;

import com.github.foodie.controllers.request.OrderRequestReq;
import com.github.foodie.services.OrderService;
import jakarta.validation.Valid;
import net.logstash.logback.marker.LogstashMarker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static net.logstash.logback.marker.Markers.append;

@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("request")
    public ResponseEntity<String> orderRequest(@RequestBody @Valid OrderRequestReq orderRequestReq) {
        LogstashMarker marker = append("method", "orderRequest");
        orderService.requestOrder(orderRequestReq);
        return ResponseEntity.ok("Order requested");
    }
}
