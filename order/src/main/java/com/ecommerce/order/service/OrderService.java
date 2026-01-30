package com.ecommerce.order.service;

import com.ecommerce.order.dto.CreateOrderRequest;
import com.ecommerce.order.dto.Item;
import com.ecommerce.order.dto.OrderCreatedEvent;
import com.ecommerce.order.dto.OrderItemRequest;
import com.ecommerce.order.entity.Order;
import com.ecommerce.order.entity.OrderItem;
import com.ecommerce.order.repository.OrderItemRepository;
import com.ecommerce.order.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;
import java.time.Instant;
import java.util.ArrayList;
import java.util.UUID;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository itemRepository;
    private final OrderEventProducer producer;

    public OrderService(OrderRepository orderRepository, OrderItemRepository itemRepository, OrderEventProducer producer) {
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
        this.producer = producer;
    }

    private List<Item> mapItems(CreateOrderRequest request) {

        List<Item> items = new ArrayList<>();

        for (OrderItemRequest reqItem : request.getItems()) {
            Item item = new Item();
            item.setProductId(reqItem.getProductId());
            item.setQuantity(reqItem.getQuantity());
            items.add(item);
        }

        return items;
    }

    @Transactional
    public Order placeOrder(CreateOrderRequest request) {

        Order order = new Order();
        order.setUserId(request.getUserId());
        order.setStatus("CREATED");
        order.setTotalAmount(request.getTotalAmount());

        Order savedOrder = orderRepository.save(order);

        request.getItems().forEach(i -> {
            OrderItem item = new OrderItem();
            item.setOrderId(savedOrder.getId());
            item.setProductId(i.getProductId());
            item.setQuantity(i.getQuantity());
            item.setPrice(i.getPrice());
            itemRepository.save(item);
        });

        OrderCreatedEvent event = new OrderCreatedEvent(
                UUID.randomUUID().toString(),
                savedOrder.getId(),
                request.getUserId(),
                request.getTotalAmount(),
                mapItems(request),
                Instant.now()
        );

        producer.sendOrderCreatedEvent(event);

        return savedOrder;
    }
}
