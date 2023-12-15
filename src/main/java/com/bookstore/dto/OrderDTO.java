package com.bookstore.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class OrderDTO {
    private Long id;
    private Long userId;
    private LocalDateTime orderDate;
    private Double totalAmount;
    private String status;

    public OrderDTO() {
    }

    public OrderDTO(Long id, Long userId, LocalDateTime orderDate, Double totalAmount, String status) {
        this.id = id;
        this.userId = userId;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.status = status;
    }
}
