package com.ecommerce.payment.service;

import com.ecommerce.payment.dto.PaymentFailedEvent;
import com.ecommerce.payment.dto.PaymentSuccessEvent;
import com.ecommerce.payment.entity.Payment;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class PaymentProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public PaymentProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishPaymentSuccess(Payment payment) {

        PaymentSuccessEvent event =
                new PaymentSuccessEvent(
                        UUID.randomUUID().toString(),
                        payment.getOrderId(),
                        payment.getUserId(),
                        payment.getAmount(),
                        payment.getTransactionId(),
                        Instant.now());

        kafkaTemplate.send("payment.success", event);
    }

    public void publishPaymentFailed(Long orderId, Long userId, String reason) {

        PaymentFailedEvent event =
                new PaymentFailedEvent(
                        UUID.randomUUID().toString(),
                        orderId,
                        userId,
                        reason,
                        Instant.now());

        kafkaTemplate.send("payment.failed", event);
    }
}
