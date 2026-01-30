package com.ecommerce.payment.service;

import com.ecommerce.payment.dto.PaymentEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
@Service
public class PaymentEventProducer {
    private final KafkaTemplate<String, PaymentEvent> kafkaTemplate;

    public PaymentEventProducer(KafkaTemplate<String, PaymentEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendPaymentSuccess(PaymentEvent event) {
        kafkaTemplate.send("payment.success", event);
    }

    public void sendPaymentFailure(PaymentEvent event) {
        kafkaTemplate.send("payment.failed", event);
    }
}
