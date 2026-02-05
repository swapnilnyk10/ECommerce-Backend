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
}
