package com.shrijal.e_commerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Order entity representing a completed purchase
 * Maps to 'orders' table in the database
 */
@Entity
@Table(name = "orders")
public class Order {

    // Primary key with auto-increment
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Timestamp when order was placed
    @Column(name = "order_date")
    private LocalDateTime orderDate;

    // Total amount in INR (base currency)
    @Positive
    @Column(name = "total_amount", nullable = false)
    private Double totalAmount;

    // Converted amount in target currency (e.g., USD, EUR)
    @Positive
    @Column(name = "converted_amount")
    private Double convertedAmount;

    // Target currency code (e.g., "USD", "EUR", "GBP")
    private String currency;

    // Order status (PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    // Many-to-One relationship: Many orders belong to one user
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // One-to-Many relationship: One order can have many order items
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    /**
     * JPA lifecycle callback - sets order date and default status before persisting
     */
    @PrePersist
    public void prePersist() {
        this.orderDate = LocalDateTime.now();
        // Set default status to PENDING if not already set
        if (this.status == null) {
            this.status = OrderStatus.PENDING;
        }
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}