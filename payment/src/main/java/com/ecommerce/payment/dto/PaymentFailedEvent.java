package com.ecommerce.payment.dto;

import java.time.Instant;

public class PaymentFailedEvent {

    private String eventId;
    private Long orderId;
    private Long userId;
    private String reason;
    private Instant timestamp;

    public PaymentFailedEvent() {}

    public PaymentFailedEvent(String eventId, Long orderId, Long userId,
                              String reason, Instant timestamp) {
        this.eventId = eventId;
        this.orderId = orderId;
        this.userId = userId;
        this.reason = reason;
        this.timestamp = timestamp;
    }

    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
}
