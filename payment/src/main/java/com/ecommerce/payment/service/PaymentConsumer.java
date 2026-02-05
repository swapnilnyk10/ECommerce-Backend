package com.ecommerce.payment.service;

import com.ecommerce.inventory.dto.InventoryReservedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentConsumer {

    private final PaymentService paymentService;

    public PaymentConsumer(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @KafkaListener(topics = "inventory.reserved", groupId = "payment-group")
    public void consumeInventoryReserved(InventoryReservedEvent event) {
        paymentService.processPayment(event);
    }
}
