package com.shrijal.e_commerce.dto.request;

import com.shrijal.e_commerce.model.OrderStatus;
import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object for updating order status
 */
public class UpdateOrderStatusDTO {

    @NotNull(message = "Order status is required")
    private OrderStatus status;

    // Getters and Setters
    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}