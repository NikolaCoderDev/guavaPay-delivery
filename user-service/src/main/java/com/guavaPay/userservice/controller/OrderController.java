package com.guavaPay.userservice.controller;

import com.guavaPay.userservice.client.feignClient.OrderFeignClient;
import com.guavaPay.userservice.dto.orderDto.AddressDto;
import com.guavaPay.userservice.dto.orderDto.OrderDetailsDto;
import com.guavaPay.userservice.dto.orderDto.OrderDto;
import com.guavaPay.userservice.dto.orderDto.OrderDtoRequest;
import com.guavaPay.userservice.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("order")
public class OrderController {

    private final OrderFeignClient orderFeignClient;
    private final OrderService orderService;

    @PostMapping
    @Operation(summary = "Save order")
    public ResponseEntity<OrderDtoRequest> save(@RequestBody OrderDtoRequest dto, @RequestHeader String authorization) {
        if (!dto.getDeliveryAddress().equals("")) {
            orderService.save(authorization, dto);
            return ResponseEntity.ok(dto);
        }
        return null;
    }

    @GetMapping("/myOrders")
    @Operation(summary = "Get user orders.", description = "View all orders [user]")
    public List<OrderDto> getOrdersByUserId() {
        return orderFeignClient.getOrdersByUserId();
    }

    @PutMapping("/cancel/{orderId}")
    @Operation(summary = "Cancel address by userId.", description = "Put token here. [user]")
    public ResponseEntity<String> cancelOrderByUserId(@PathVariable("orderId") String orderId) {
        return orderFeignClient.cancelOrderByUserId(orderId);
    }

    @PutMapping("/{orderId}")
    @Operation(summary = "Update address by userId.", description = "Put token here. [user]")
    public ResponseEntity<String> updateAddressByUserId(@RequestBody AddressDto address, @PathVariable("orderId") String orderId) {
        return orderFeignClient.updateAddressByUserId(address, orderId);
    }

    @GetMapping("/details")
    @Operation(summary = "Get my order details. [user permission]")
    public List<OrderDetailsDto> getUserOrdersDetail() {
        return orderService.getUserOrdersDetail();
    }
}