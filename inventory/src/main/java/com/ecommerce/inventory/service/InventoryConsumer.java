package com.ecommerce.inventory.service;

import com.ecommerce.order.dto.OrderCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class InventoryConsumer {

    private final InventoryService inventoryService;

    public InventoryConsumer(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @KafkaListener(topics = "order.created", groupId = "inventory-group")
    public void consumeOrderCreated(OrderCreatedEvent event) {
        inventoryService.reserveInventory(event);
    }

//    @KafkaListener(topics = "payment.failed", groupId = "inventory-group")
//    public void consumePaymentFailed(PaymentFailedEvent event) {
//        inventoryService.rollbackInventory(event);
//    }

//    @KafkaListener(topics = "payment.success", groupId = "inventory-group")
//    public void consumePaymentSuccess(PaymentSuccessEvent event) {
//        inventoryService.finalizeInventory(event);
//    }


    @KafkaListener(topics = "order.success", groupId = "inventory-group")
    public void consumeOrderSuccess(OrderCreatedEvent event) {inventoryService.finalizeInventory(event);}

    @KafkaListener(topics = "order.failed", groupId = "inventory-group")
    public void consumeOrderFailed(OrderCreatedEvent event) {inventoryService.rollbackInventory(event);}
}
