package com.ecommerce.payment.dto;

import java.math.BigDecimal;
import java.time.Instant;

public class PaymentSuccessEvent {

    private String eventId;
    private Long orderId;
    private Long userId;
    private BigDecimal amount;
    private String transactionId;
    private Instant timestamp;

    public PaymentSuccessEvent() {}

    public PaymentSuccessEvent(String eventId, Long orderId, Long userId,
                               BigDecimal amount, String transactionId, Instant timestamp) {
        this.eventId = eventId;
        this.orderId = orderId;
        this.userId = userId;
        this.amount = amount;
        this.transactionId = transactionId;
        this.timestamp = timestamp;
    }

    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }

    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
}
