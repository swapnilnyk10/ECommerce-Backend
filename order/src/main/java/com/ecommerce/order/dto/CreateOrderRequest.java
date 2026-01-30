package com.ecommerce.order.dto;

import java.math.BigDecimal;
import java.util.List;

public class CreateOrderRequest {
    private Long userId;
    private BigDecimal totalAmount;
    private List<OrderItemRequest> items;

    public CreateOrderRequest() {}

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<OrderItemRequest> getItems() {
        return items;
    }

    public void setItems(List<OrderItemRequest> items) {
        this.items = items;
    }
}
