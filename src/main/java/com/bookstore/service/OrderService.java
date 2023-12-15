package com.bookstore.service;

import com.bookstore.dto.OrderDTO;
import java.util.List;

public interface OrderService {
    OrderDTO findById(Long id);
    List<OrderDTO> findAll();
    OrderDTO save(OrderDTO orderDTO);
    OrderDTO update(Long id, OrderDTO orderDTO);
    void deleteById(Long id);
}

