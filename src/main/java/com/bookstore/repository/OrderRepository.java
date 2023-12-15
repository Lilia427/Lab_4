package com.bookstore.repository;

import com.bookstore.entity.Order;
import com.bookstore.storage.OrderFileStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class OrderRepository {

    private final OrderFileStorage orderFileStorage;

    @Autowired
    public OrderRepository(OrderFileStorage orderFileStorage) {
        this.orderFileStorage = orderFileStorage;
    }

    public Optional<Order> findById(Long id) {
        return orderFileStorage.findById(id);
    }

    public List<Order> findAll() {
        return orderFileStorage.findAll();
    }

    public Order save(Order order) {
        return orderFileStorage.save(order);
    }

    public void deleteById(Long id) {
        orderFileStorage.deleteById(id);
    }
}
