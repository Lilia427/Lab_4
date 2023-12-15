package com.bookstore.service.impl;

import com.bookstore.dto.OrderDTO;
import com.bookstore.entity.Order;
import com.bookstore.repository.OrderRepository;
import com.bookstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderDTO findById(Long id) {
        return orderRepository.findById(id).map(this::convertToDTO).orElse(null);
    }

    @Override
    public List<OrderDTO> findAll() {
        return orderRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public OrderDTO save(OrderDTO orderDTO) {
        Order order = convertToEntity(orderDTO);
        Order savedOrder = orderRepository.save(order);
        return convertToDTO(savedOrder);
    }

    @Override
    public OrderDTO update(Long id, OrderDTO orderDTO) {
        Order existingOrder = orderRepository.findById(id).orElse(null);
        if (existingOrder != null) {
            updateExistingOrder(existingOrder, orderDTO);
            orderRepository.save(existingOrder);
            return convertToDTO(existingOrder);
        }
        return null;
    }

    @Override
    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }

    private OrderDTO convertToDTO(Order order) {
        return new OrderDTO(order.getId(), order.getUserId(), order.getOrderDate(), order.getTotalAmount(), order.getStatus());
    }

    private Order convertToEntity(OrderDTO orderDTO) {
        return new Order(orderDTO.getId(), orderDTO.getUserId(), LocalDateTime.now(), orderDTO.getTotalAmount(), orderDTO.getStatus());
    }

    private void updateExistingOrder(Order order, OrderDTO orderDTO) {
        order.setUserId(orderDTO.getUserId());
        order.setTotalAmount(orderDTO.getTotalAmount());
        order.setStatus(orderDTO.getStatus());
    }
}

