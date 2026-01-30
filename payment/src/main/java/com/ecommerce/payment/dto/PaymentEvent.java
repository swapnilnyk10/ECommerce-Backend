package com.ecommerce.payment.dto;

import java.math.BigDecimal;
import java.time.Instant;

public class PaymentEvent {

    private String eventId;
    private Long orderId;
    private Long paymentId;
    private BigDecimal amount;
    private String status;
    private String mode;
    private Instant timestamp;

    public PaymentEvent() {
    }

    public PaymentEvent(String eventId, Long orderId, Long paymentId, BigDecimal amount, String status, String mode, Instant timestamp) {
        this.eventId = eventId;
        this.orderId = orderId;
        this.paymentId = paymentId;
        this.amount = amount;
        this.status = status;
        this.mode = mode;
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

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
