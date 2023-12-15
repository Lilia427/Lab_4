package com.bookstore.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Order {
    private Long id;
    private Long userId;
    private LocalDateTime orderDate;
    private Double totalAmount;
    private String status;

    public Order() {
    }

    public Order(Long id, Long userId, LocalDateTime orderDate, Double totalAmount, String status) {
        this.id = id;
        this.userId = userId;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.status = status;
    }
}
