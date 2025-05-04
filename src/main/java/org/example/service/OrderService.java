package org.example.service;

import org.example.model.Order;
import org.example.model.Product;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService {
    private final Map<Long, Order> orders = new HashMap<>();
    private long nextId = 1;

    public Order getOrder(Long id) {
        return orders.get(id);
    }

    public Order createOrder(Order order) {
        order.setId(nextId++);
        orders.put(order.getId(), order);
        return order;
    }

    public Order updateOrder(Long id, Order updated) {
        Order existing = orders.get(id);
        if (existing != null) {
            existing.setCustomer(updated.getCustomer());
            existing.setProducts(updated.getProducts());
        }
        return existing;
    }

    public Order addProduct(Long orderId, Product product) {
        Order order = orders.get(orderId);
        if (order != null) {
            order.getProducts().add(product);
        }
        return order;
    }

    public Order removeProduct(Long orderId, Long productId) {
        Order order = orders.get(orderId);
        if (order != null) {
            order.getProducts().removeIf(p -> p.getId().equals(productId));
        }
        return order;
    }

    public Order deleteOrder(Long id) {
        return orders.remove(id);
    }
}
