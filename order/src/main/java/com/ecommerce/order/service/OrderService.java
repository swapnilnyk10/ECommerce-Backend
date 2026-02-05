package com.ecommerce.order.service;

import com.ecommerce.order.dto.OrderRequest;
import com.ecommerce.order.entity.Order;
import com.ecommerce.order.entity.OrderItem;
import com.ecommerce.order.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderProducer orderProducer;

    public OrderService(OrderRepository orderRepository, OrderProducer orderProducer) {
        this.orderRepository = orderRepository;
        this.orderProducer = orderProducer;
    }

    @Transactional
    public Long createOrder(OrderRequest request) {

        Order order = new Order();
        order.setUserId(request.getUserId());
        order.setStatus("CREATED");
        order.setCreatedAt(Instant.now());

        List<OrderItem> items = request.getItems().stream()
                .map(req -> {
                    OrderItem item = new OrderItem();
                    item.setProductId(req.getProductId());
                    item.setQuantity(req.getQuantity());
                    item.setPrice(req.getPrice());
                    item.setOrder(order);
                    return item;
                }).collect(Collectors.toList());

        order.setItems(items);

        BigDecimal total = items.stream()
                .map(i -> i.getPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setTotalAmount(total);

        Order saved = orderRepository.save(order);

        orderProducer.publishOrderCreated(saved);

        return saved.getId();
    }
}
