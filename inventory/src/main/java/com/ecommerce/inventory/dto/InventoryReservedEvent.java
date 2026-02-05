package com.ecommerce.inventory.dto;

import java.math.BigDecimal;
import java.time.Instant;

public class InventoryReservedEvent {

    private String eventId;
    private Long orderId;
    private Long userId;
    private BigDecimal amount;
    private Instant timestamp;

    public InventoryReservedEvent() {}

    public InventoryReservedEvent(String eventId, Long orderId, Long userId,
                                  BigDecimal amount, Instant timestamp) {
        this.eventId = eventId;
        this.orderId = orderId;
        this.userId = userId;
        this.amount = amount;
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
