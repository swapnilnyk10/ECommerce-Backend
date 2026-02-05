package com.ecommerce.order.service;

import com.ecommerce.order.dto.Item;
import com.ecommerce.order.dto.OrderCreatedEvent;
import com.ecommerce.order.entity.Order;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderProducer {

    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

    public OrderProducer(KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    //Used to publish newly created orders through APIs
    public void publishOrderCreated(Order order) {

        List<Item> items = order.getItems().stream()
                .map(i -> new Item(i.getProductId(), i.getQuantity(), i.getPrice()))
                .collect(Collectors.toList());

        OrderCreatedEvent event = new OrderCreatedEvent(
                UUID.randomUUID().toString(),
                order.getId(),
                order.getUserId(),
                items,
                order.getTotalAmount(),
                Instant.now()
        );

        kafkaTemplate.send("order.created", event);
    }

    //Used to publish when payment is successfull (Consumer is Inventory to remove items from reserve)
    public void publishOrderSuccess(Order order) {
        List<Item> items = order.getItems().stream()
                .map(i -> new Item(i.getProductId(), i.getQuantity(), i.getPrice()))
                .collect(Collectors.toList());

        OrderCreatedEvent event = new OrderCreatedEvent(
                UUID.randomUUID().toString(),
                order.getId(),
                order.getUserId(),
                items,
                order.getTotalAmount(),
                Instant.now()
        );
        kafkaTemplate.send("order.success", event);
    }

    //Used to publish when payment is failed (Consumer is Inventory to remove items from reserve and add in available)
    public void publishOrderFailed(Order order) {
        List<Item> items = order.getItems().stream()
                .map(i -> new Item(i.getProductId(), i.getQuantity(), i.getPrice()))
                .collect(Collectors.toList());

        OrderCreatedEvent event = new OrderCreatedEvent(
                UUID.randomUUID().toString(),
                order.getId(),
                order.getUserId(),
                items,
                order.getTotalAmount(),
                Instant.now()
        );
        kafkaTemplate.send("order.failed", event);
    }
}
