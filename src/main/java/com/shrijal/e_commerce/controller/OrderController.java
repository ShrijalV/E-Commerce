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

/**
 * REST Controller for Order operations
 * Handles order placement with currency conversion, status updates, and order deletion
 */
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * Place an order
     * Converts cart items to order and performs currency conversion using Frankfurter API
     * @param dto - order request with user ID and target currency
     * @return OrderResponseDTO with order details and converted amount
     */
    @PostMapping("/place")
    public ResponseEntity<OrderResponseDTO> place(@Valid @RequestBody OrderRequestDTO dto) {
        OrderResponseDTO response = orderService.place(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Get order by ID
     * @param id - order ID
     * @return OrderResponseDTO with complete order details
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getById(@PathVariable Long id) {
        OrderResponseDTO response = orderService.getOrderById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Update order status
     * @param id - order ID
     * @param dto - update status request
     * @return Updated OrderResponseDTO
     */
    @PutMapping("/{id}/status")
    public ResponseEntity<OrderResponseDTO> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateOrderStatusDTO dto) {
        OrderResponseDTO response = orderService.updateStatus(id, dto.getStatus());
        return ResponseEntity.ok(response);
    }

    /**
     * Delete an order
     * @param id - order ID
     * @return Success message
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        orderService.delete(id);
        return ResponseEntity.ok("Order deleted successfully");
    }
}