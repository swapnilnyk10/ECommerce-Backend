package com.ecommerce.order.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {
    @Bean
    public NewTopic orderCreatedTopic() {
        return new NewTopic("order.created", 3,(short) 1);
    }

    @Bean
    public ModelMapper  modelMapper() { return new ModelMapper(); }
}
