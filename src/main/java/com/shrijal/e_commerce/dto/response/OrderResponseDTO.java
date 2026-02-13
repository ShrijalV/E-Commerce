package com.shrijal.e_commerce.dto.response;

import com.shrijal.e_commerce.model.OrderStatus;
import java.util.List;

/**
 * Data Transfer Object for Order responses
 * Includes both original amount (INR) and converted amount in target currency
 */
public class OrderResponseDTO {

    private Long orderId;
    private Double totalAmount;        // Amount in INR
    private Double convertedAmount;    // Amount in target currency
    private String currency;           // Target currency code (e.g., USD, EUR)
    private OrderStatus status;        // Order status (PENDING, CONFIRMED, etc.)
    private List<OrderItemResponseDTO> items;

    // Getters and Setters
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getConvertedAmount() {
        return convertedAmount;
    }

    public void setConvertedAmount(Double convertedAmount) {
        this.convertedAmount = convertedAmount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public List<OrderItemResponseDTO> getItems() {
        return items;
    }

    public void setItems(List<OrderItemResponseDTO> items) {
        this.items = items;
    }
}