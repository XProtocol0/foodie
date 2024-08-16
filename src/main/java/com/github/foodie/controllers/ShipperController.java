package com.github.foodie.controllers;

import com.github.foodie.controllers.request.RegisterShipperReq;
import com.github.foodie.controllers.response.GenericResponse;
import com.github.foodie.dtos.OrderDto;
import com.github.foodie.services.ShipperService;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.marker.LogstashMarker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static net.logstash.logback.marker.Markers.append;

@RestController
@RequestMapping("/shipper")
@Slf4j
public class ShipperController {

    @Autowired
    private ShipperService shipperService;

    @PostMapping("/register")
    public ResponseEntity<GenericResponse<String>> registerShipper(@RequestBody RegisterShipperReq registerShipperReq) {
        LogstashMarker marker = append("method", "registerShipper");
        log.info(marker, "registering shipper");
        shipperService.registerShipper(registerShipperReq);
        return ResponseEntity.ok(
                new GenericResponse<>(true, "Shipper registered successfully", null));
    }

    @GetMapping("/accept")
    public ResponseEntity<GenericResponse<String>> acceptOrder(@RequestParam UUID orderRequestId) {
        LogstashMarker markers = append("method", "acceptOrder");
        log.info(markers, "accepting order reqest: {}", orderRequestId);
        shipperService.acceptOrderDelivery(orderRequestId);
        return ResponseEntity.ok(
                new GenericResponse<>(true, "Order accepted.", null));
    }

    @GetMapping("/active/delivery")
    private ResponseEntity<GenericResponse<UUID>> getActiveOrderDelivery() {
        LogstashMarker markers = append("method", "getActiveOrderDelivery");
        log.info(markers, "getting active order delivery");
        return ResponseEntity.ok(new GenericResponse<>(shipperService.getActiveOrder()));
    }

    @GetMapping("/pickup")
    private ResponseEntity<GenericResponse<String>> pickUpOrder(@RequestParam UUID orderId) {
        LogstashMarker markers = append("method", "pickUpOrder");
        log.info(markers, "updating order status for pick up");
        shipperService.updateOrderAfterPickUp(orderId);
        return ResponseEntity.ok(new GenericResponse<>(true, "Updated order status", null));
    }

    @GetMapping("/delivered")
    private ResponseEntity<GenericResponse<OrderDto>> finishDelivery(@RequestParam UUID orderId) {
        LogstashMarker markers = append("method", "finishDelivery");
        log.info(markers, "finish delivery");
        OrderDto dto = shipperService.orderDelivered(orderId);
        return ResponseEntity.ok(
                new GenericResponse<>(true, "Thank you for delivering order.", dto));
    }
}
