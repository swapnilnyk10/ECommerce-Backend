package com.ecommerce.inventory.dto;

import java.time.Instant;

public class InventoryFailedEvent {

    private String eventId;
    private Long orderId;
    private String reason;
    private Instant timestamp;

    public InventoryFailedEvent() {}

    public InventoryFailedEvent(String eventId, Long orderId, String reason, Instant timestamp) {
        this.eventId = eventId;
        this.orderId = orderId;
        this.reason = reason;
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
