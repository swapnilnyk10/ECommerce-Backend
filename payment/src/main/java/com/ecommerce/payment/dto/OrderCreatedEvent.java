package com.ecommerce.payment.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public class OrderCreatedEvent {

        private String eventId;
        private Long orderId;
        private Long userId;
        private BigDecimal amount;
        private List<Item> items;
        private Instant timestamp;

    public OrderCreatedEvent(String eventId, Long orderId, Long userId, BigDecimal amount, List<Item> items, Instant timestamp) {
        this.eventId = eventId;
        this.orderId = orderId;
        this.userId = userId;
        this.amount = amount;
        this.items = items;
        this.timestamp = timestamp;
    }

    public OrderCreatedEvent() {
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
