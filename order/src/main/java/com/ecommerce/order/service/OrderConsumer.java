package com.ecommerce.order.service;

import com.ecommerce.inventory.dto.InventoryFailedEvent;
import com.ecommerce.order.repository.OrderRepository;
import com.ecommerce.payment.dto.PaymentSuccessEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderConsumer {

    private final OrderRepository orderRepository;

    public OrderConsumer(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @KafkaListener(topics = "payment.success", groupId = "order-group")
    public void consumePaymentSuccess(PaymentSuccessEvent event) {
        updateOrderStatus(event.getOrderId(), "CONFIRMED");
    }

    @KafkaListener(topics = "inventory.failed", groupId = "order-group")
    public void consumeInventoryFailed(InventoryFailedEvent event) {
        updateOrderStatus(event.getOrderId(), "CANCELLED");
    }

    private void updateOrderStatus(Long orderId, String status) {
        orderRepository.findById(orderId).ifPresent(order -> {
            order.setStatus(status);
            orderRepository.save(order);
        });
    }
}
