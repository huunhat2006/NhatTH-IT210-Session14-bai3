package com.restaurant.it210session14bai3.repository;


import com.restaurant.it210session14bai3.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}