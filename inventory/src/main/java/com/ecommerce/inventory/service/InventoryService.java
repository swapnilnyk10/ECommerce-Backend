package com.ecommerce.inventory.service;

import com.ecommerce.inventory.entity.Inventory;
import com.ecommerce.inventory.repository.InventoryRepository;
import com.ecommerce.inventory.utils.InventoryNotFoundException;
import com.ecommerce.order.dto.Item;
import com.ecommerce.order.dto.OrderCreatedEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
                    .orElse(null);

            if(inventory == null) {
                producer.publishInventoryFailed(event.getOrderId(), "PRODUCT_NOT_FOUND");
                return;
            }

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
    public void rollbackInventory(OrderCreatedEvent event) {
        for(Item item : event.getItems()) {
            Inventory inventory = repository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found: " + item.getProductId()));
            inventory.setReservedQuantity(inventory.getReservedQuantity() - item.getQuantity());
            inventory.setAvailableQuantity(inventory.getAvailableQuantity() + item.getQuantity());

        }
        // Restore reserved stock
        // We must know order items -> normally fetched from Order Service or stored locally

        // Simplified version: skip item recovery for now
    }

    @Transactional
    public void finalizeInventory(OrderCreatedEvent event) {

        for(Item item : event.getItems()) {
            Inventory inventory = repository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found: " + item.getProductId()));
            inventory.setReservedQuantity(inventory.getReservedQuantity() - item.getQuantity());
        }
        // On success, just clear reserved quantity
        // Deduction already happened in reservation stage
    }

    @Transactional
    public Inventory createOrUpdateInventory(Inventory request) {

        Inventory inventory = repository.findById(request.getProductId())
                .orElse(null);

        if (inventory == null) {
            // New product inventory
            inventory = new Inventory();
            inventory.setProductId(request.getProductId());
            inventory.setAvailableQuantity(request.getAvailableQuantity());
            inventory.setReservedQuantity(0);
        } else {
            // Existing product → increment stock
            inventory.setAvailableQuantity(
                    inventory.getAvailableQuantity() + request.getAvailableQuantity());
        }

        return repository.save(inventory);
    }

    public Inventory getInventoryById(Long productId) {
        return repository.findById(productId).orElseThrow(() -> new InventoryNotFoundException("Inventory not found with productId: " + productId));
    }

    public List<Inventory> getAllInventory() {
        return repository.findAll();
    }
}
