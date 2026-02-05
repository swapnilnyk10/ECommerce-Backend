package com.ecommerce.order.dto;


import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public class OrderCreatedEvent {

    private String eventId;
    private Long orderId;
    private Long userId;
    private List<Item> items;
    private BigDecimal totalAmount;
    private Instant timestamp;

    public OrderCreatedEvent() {}

    public OrderCreatedEvent(String eventId, Long orderId, Long userId,
                             List<Item> items, BigDecimal totalAmount, Instant timestamp) {
        this.eventId = eventId;
        this.orderId = orderId;
        this.userId = userId;
        this.items = items;
        this.totalAmount = totalAmount;
        this.timestamp = timestamp;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
