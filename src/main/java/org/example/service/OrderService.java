package org.example.service;

import org.example.model.Order;
import org.example.model.Product;
import org.example.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public Optional<Order> getOrder(Long id) {
        return repository.findById(id);
    }

    public Order createOrder(Order order) {
        return repository.save(order);
    }

    public Optional<Order> updateOrder(Long id, Order updated) {
        return repository.findById(id).map(existing -> {
            existing.setCustomer(updated.getCustomer());
            existing.setProducts(updated.getProducts());
            return repository.save(existing);
        });
    }

    public Optional<Order> addProduct(Long orderId, Product product) {
        return repository.findById(orderId).map(order -> {
            order.getProducts().add(product);
            return repository.save(order);
        });
    }

    public Optional<Order> removeProduct(Long orderId, Long productId) {
        return repository.findById(orderId).map(order -> {
            order.getProducts().removeIf(p -> p.getId().equals(productId));
            return repository.save(order);
        });
    }

    public void deleteOrder(Long id) {
        repository.deleteById(id);
    }

    public List<Order> getAll() {
        return repository.findAll();
    }
}
