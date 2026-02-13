package com.shrijal.e_commerce.dto.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object for placing orders
 * Includes currency for Frankfurter API conversion
 */
public class OrderRequestDTO {

    @NotNull(message = "User id is required")
    private Long userId;

    @NotBlank(message = "Currency is required (e.g. USD, EUR, GBP)")
    private String currency;

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
