package com.shrijal.e_commerce.controller;

import com.shrijal.e_commerce.dto.request.OrderRequestDTO;
import com.shrijal.e_commerce.dto.request.UpdateOrderStatusDTO;
import com.shrijal.e_commerce.dto.response.OrderResponseDTO;
import com.shrijal.e_commerce.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    //placing an order
    @PostMapping("/place")
    public ResponseEntity<OrderResponseDTO> place(@Valid @RequestBody OrderRequestDTO dto) {
        OrderResponseDTO response = orderService.place(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    //get order by order id
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getById(@PathVariable Long id) {
        OrderResponseDTO response = orderService.getOrderById(id);
        return ResponseEntity.ok(response);
    }

    //update order status
    @PutMapping("/{id}/status")
    public ResponseEntity<OrderResponseDTO> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateOrderStatusDTO dto) {
        OrderResponseDTO response = orderService.updateStatus(id, dto.getStatus());
        return ResponseEntity.ok(response);
    }

    //delete order
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        orderService.delete(id);
        return ResponseEntity.ok("Order deleted successfully");
    }
}