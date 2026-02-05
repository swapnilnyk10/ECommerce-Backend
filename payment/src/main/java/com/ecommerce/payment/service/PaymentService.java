package com.ecommerce.payment.service;

import com.ecommerce.inventory.dto.InventoryReservedEvent;
import com.ecommerce.payment.entity.Payment;
import com.ecommerce.payment.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
public class PaymentService {

    private final PaymentRepository repository;
    private final PaymentProducer producer;

    public PaymentService(PaymentRepository repository, PaymentProducer producer) {
        this.repository = repository;
        this.producer = producer;
    }

    @Transactional
    public void processPayment(InventoryReservedEvent event) {

        Payment payment = new Payment();
        payment.setOrderId(event.getOrderId());
        payment.setUserId(event.getUserId());
        payment.setAmount(event.getAmount());
        payment.setCreatedAt(Instant.now());

        boolean success = simulatePayment();

        if (success) {
            payment.setStatus("SUCCESS");
            payment.setTransactionId(UUID.randomUUID().toString());

            repository.save(payment);

            producer.publishPaymentSuccess(payment);

        } else {
            payment.setStatus("FAILED");
            payment.setTransactionId(null);

            repository.save(payment);

            producer.publishPaymentFailed(event.getOrderId(), event.getUserId(), "PAYMENT_DECLINED");
        }
    }

    private boolean simulatePayment() {
        return Math.random() < 0.85;   // 85% success simulation
    }
}
