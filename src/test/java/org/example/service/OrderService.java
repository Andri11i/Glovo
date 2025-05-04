package org.example.service;

import org.example.model.Order;
import org.example.model.Product;
import org.example.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    private OrderRepository orderRepository;
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderRepository = mock(OrderRepository.class);
        orderService = new OrderService(orderRepository);
    }

    @Test
    void testCreateOrder() {
        Order order = new Order("Customer A", List.of(new Product(1L, "Test Product", 2)));

        when(orderRepository.save(order)).thenReturn(order);

        Order saved = orderService.createOrder(order);
        assertEquals("Customer A", saved.getCustomer());
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testGetOrder() {
        Order order = new Order("Customer B", List.of());
        order.setId(1L);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        Optional<Order> result = orderService.getOrder(1L);
        assertTrue(result.isPresent());
        assertEquals("Customer B", result.get().getCustomer());
    }

    @Test
    void testUpdateOrder() {
        Order existing = new Order("Old", List.of(new Product(1L, "Old", 1)));
        existing.setId(1L);
        Order updated = new Order("New", List.of(new Product(2L, "New", 2)));

        when(orderRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(orderRepository.save(any(Order.class))).thenAnswer(i -> i.getArgument(0));

        Optional<Order> result = orderService.updateOrder(1L, updated);
        assertTrue(result.isPresent());
        assertEquals("New", result.get().getCustomer());
        assertEquals(1, result.get().getProducts().size());
    }

    @Test
    void testAddProduct() {
        Product product = new Product(3L, "Mouse", 1);
        Order order = new Order("Client", List.of());
        order.setId(1L);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        Optional<Order> result = orderService.addProduct(1L, product);
        assertTrue(result.isPresent());
        assertEquals(1, result.get().getProducts().size());
        assertEquals("Mouse", result.get().getProducts().get(0).getName());
    }

    @Test
    void testRemoveProduct() {
        Product p1 = new Product(1L, "Laptop", 1);
        Product p2 = new Product(2L, "Keyboard", 1);
        Order order = new Order("Client", List.of(p1, p2));
        order.setId(1L);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        Optional<Order> result = orderService.removeProduct(1L, 2L);
        assertTrue(result.isPresent());
        assertEquals(1, result.get().getProducts().size());
        assertEquals("Laptop", result.get().getProducts().get(0).getName());
    }

    @Test
    void testDeleteOrder() {
        orderService.deleteOrder(1L);
        verify(orderRepository, times(1)).deleteById(1L);
    }
}
