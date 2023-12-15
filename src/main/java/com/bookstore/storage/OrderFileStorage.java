package com.bookstore.storage;

import com.bookstore.entity.Order;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class OrderFileStorage {

    private final File file;
    private final ObjectMapper objectMapper;

    public OrderFileStorage(@Value("C:\\lab_4\\Order") String filePath, ObjectMapper objectMapper) {
        this.file = new File(filePath);
        this.objectMapper = objectMapper;
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException("Cannot create new file for orders", e);
            }
        }
    }

    public List<Order> findAll() {
        try {
            return objectMapper.readValue(file, new TypeReference<List<Order>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public Optional<Order> findById(Long id) {
        return findAll().stream().filter(order -> order.getId().equals(id)).findFirst();
    }

    public Order save(Order order) {
        List<Order> orders = findAll();
        orders.removeIf(o -> o.getId().equals(order.getId()));
        orders.add(order);
        writeToFile(orders);
        return order;
    }

    public void deleteById(Long id) {
        List<Order> orders = findAll();
        orders.removeIf(order -> order.getId().equals(id));
        writeToFile(orders);
    }

    private void writeToFile(List<Order> orders) {
        try {
            objectMapper.writeValue(file, orders);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
