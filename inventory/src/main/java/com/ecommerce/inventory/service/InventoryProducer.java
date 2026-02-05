package com.ecommerce.inventory.service;

import com.ecommerce.inventory.dto.InventoryFailedEvent;
import com.ecommerce.inventory.dto.InventoryReservedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Component
public class InventoryProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public InventoryProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishInventoryReserved(Long orderId, Long userId, BigDecimal Amount) {
        InventoryReservedEvent event =
                new InventoryReservedEvent(UUID.randomUUID().toString(), orderId, userId, Amount, Instant.now());
        kafkaTemplate.send("inventory.reserved", event);
    }

    public void publishInventoryFailed(Long orderId, String reason) {
        InventoryFailedEvent event =
                new InventoryFailedEvent(UUID.randomUUID().toString(), orderId, reason, Instant.now());
        kafkaTemplate.send("inventory.failed", event);
    }

}
