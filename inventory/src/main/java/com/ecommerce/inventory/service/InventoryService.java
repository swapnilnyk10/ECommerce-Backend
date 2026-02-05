package com.ecommerce.inventory.service;

import com.ecommerce.inventory.entity.Inventory;
import com.ecommerce.inventory.repository.InventoryRepository;
import com.ecommerce.order.dto.Item;
import com.ecommerce.order.dto.OrderCreatedEvent;
import com.ecommerce.payment.dto.PaymentFailedEvent;
import com.ecommerce.payment.dto.PaymentSuccessEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryService {

    private final InventoryRepository repository;
    private final InventoryProducer producer;

    public InventoryService(InventoryRepository repository, InventoryProducer producer) {
        this.repository = repository;
        this.producer = producer;
    }

    @Transactional
    public void reserveInventory(OrderCreatedEvent event) {

        for (Item item : event.getItems()) {

            Inventory inventory = repository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            if (inventory.getAvailableQuantity() < item.getQuantity()) {
                producer.publishInventoryFailed(event.getOrderId(), "OUT_OF_STOCK");
                return;
            }
        }

        for (Item item : event.getItems()) {

            Inventory inventory = repository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found: " + item.getProductId()));


            inventory.setAvailableQuantity(inventory.getAvailableQuantity() - item.getQuantity());
            inventory.setReservedQuantity(inventory.getReservedQuantity() + item.getQuantity());

            repository.save(inventory);
        }

        producer.publishInventoryReserved(event.getOrderId(), event.getUserId(), event.getTotalAmount());
    }

    @Transactional
    public void rollbackInventory(PaymentFailedEvent event) {

        // Restore reserved stock
        // We must know order items -> normally fetched from Order Service or stored locally

        // Simplified version: skip item recovery for now
    }

    @Transactional
    public void finalizeInventory(PaymentSuccessEvent event) {

        // On success, just clear reserved quantity
        // Deduction already happened in reservation stage
    }
}
