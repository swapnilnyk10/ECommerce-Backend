package com.ecommerce.inventory.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic inventoryReservedTopic() {
        return new NewTopic("inventory.reserved", 3, (short) 1);
    }

    @Bean
    public NewTopic inventoryFailedTopic() {
        return new NewTopic("inventory.failed", 3, (short) 1);
    }
}
