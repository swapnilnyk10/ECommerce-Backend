package com.ecommerce.payment.service;

import com.ecommerce.payment.dto.OrderCreatedEvent;
import com.ecommerce.payment.dto.PaymentEvent;
import com.ecommerce.payment.entity.Payment;
import com.ecommerce.payment.repository.PaymentRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Component
public class OrderEventConsumer {

    private final PaymentRepository paymentRepository;
    private final PaymentEventProducer producer;

    public OrderEventConsumer(PaymentRepository paymentRepository, PaymentEventProducer producer) {
        this.paymentRepository = paymentRepository;
        this.producer = producer;
    }

    @KafkaListener(topics = "order.created", groupId = "payment-group")
    @Transactional
    public void consume(OrderCreatedEvent event) {

        System.out.println("Received Order Event: " + event.getOrderId());

        boolean paymentSuccess = simulatePayment();

        Payment payment = new Payment();
        payment.setOrderId(event.getOrderId());
        payment.setAmount(event.getAmount());
        payment.setMode("UPI");

        if (paymentSuccess) {
            payment.setStatus("SUCCESS");
        } else {
            payment.setStatus("FAILED");
        }

        payment.setTransactionId(UUID.randomUUID().toString());

        Payment saved = paymentRepository.save(payment);

        PaymentEvent paymentEvent = new PaymentEvent(
                UUID.randomUUID().toString(),
                event.getOrderId(),
                saved.getId(),
                event.getAmount(),
                payment.getStatus(),
                payment.getMode(),
                Instant.now()
        );

        if (paymentSuccess) {
            producer.sendPaymentSuccess(paymentEvent);
        } else {
            producer.sendPaymentFailure(paymentEvent);
        }
    }

    private boolean simulatePayment() {
        return Math.random() > 0.1; // 90% success rate
    }
}
