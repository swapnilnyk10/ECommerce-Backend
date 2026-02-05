package com.ecommerce.order.service;

import com.ecommerce.inventory.dto.InventoryFailedEvent;
import com.ecommerce.order.entity.Order;
import com.ecommerce.order.repository.OrderRepository;
import com.ecommerce.payment.dto.PaymentFailedEvent;
import com.ecommerce.payment.dto.PaymentSuccessEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class OrderConsumer {

    private final OrderRepository orderRepository;
    private final OrderProducer orderProducer;
    public OrderConsumer(OrderRepository orderRepository,  OrderProducer orderProducer) {
        this.orderRepository = orderRepository;
        this.orderProducer = orderProducer;
    }

    @KafkaListener(topics = "payment.success", groupId = "order-group")
    @Transactional
    public void consumePaymentSuccess(PaymentSuccessEvent event) {
        updateOrderStatus(event.getOrderId(), "PAYMENT_SUCCESS_ORDER_CONFIRMED");
        Order order = orderRepository.findById(event.getOrderId()).orElseThrow(() -> new RuntimeException("Order not found: " + event.getOrderId()));
        orderProducer.publishOrderSuccess(order);
    }

    @KafkaListener(topics = "payment.failed", groupId = "order-group")
    @Transactional
    public void consumePaymentFailed(PaymentFailedEvent event) {
        updateOrderStatus(event.getOrderId(), "PAYMENT_FAILED");
        Order order = orderRepository.findById(event.getOrderId()).orElseThrow(() -> new RuntimeException("Order not found: " + event.getOrderId()));
        orderProducer.publishOrderFailed(order);
    }

    @KafkaListener(topics = "inventory.failed", groupId = "order-group")
    public void consumeInventoryFailed(InventoryFailedEvent event) {
        updateOrderStatus(event.getOrderId(), event.getReason());
    }

    private void updateOrderStatus(Long orderId, String status) {
        orderRepository.findById(orderId).ifPresent(order -> {
            order.setStatus(status);
            orderRepository.save(order);
        });
    }
}
