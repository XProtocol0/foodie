package com.github.foodie.controllers;

import com.github.foodie.controllers.request.OrderRequestReq;
import com.github.foodie.controllers.response.GenericResponse;
import com.github.foodie.services.OrderRequestService;
import com.github.foodie.services.OrderService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.marker.LogstashMarker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static net.logstash.logback.marker.Markers.append;

@RestController
@RequestMapping("order")
@Slf4j
public class OrderController {

    @Autowired
    private OrderRequestService orderRequestService;
    @Autowired
    private OrderService orderService;

    @PostMapping("request")
    public ResponseEntity<GenericResponse<String>> orderRequest(@RequestBody @Valid OrderRequestReq orderRequestReq) {
        LogstashMarker marker = append("method", "orderRequest");
        log.info(marker, "requesting order");
        orderRequestService.requestOrder(orderRequestReq);
        return ResponseEntity.ok(
                new GenericResponse<>(true, "Order requested", null));
    }
}
