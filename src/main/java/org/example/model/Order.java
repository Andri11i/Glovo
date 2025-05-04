package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private Long id;
    private String customer;
    private List<Product> products = new ArrayList<>();

    public Order() {
    }

    public Order(Long id, String customer) {
        this.id = id;
        this.customer = customer;
        this.products = new ArrayList<>();
    }

    public Order(Long id, String customer, List<Product> products) {
        this.id = id;
        this.customer = customer;
        this.products = products;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
