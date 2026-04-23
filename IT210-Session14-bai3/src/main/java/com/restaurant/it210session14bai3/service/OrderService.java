package com.restaurant.it210session14bai3.service;

import com.restaurant.it210session14bai3.entity.Order;
import com.restaurant.it210session14bai3.entity.Product;
import com.restaurant.it210session14bai3.repository.OrderRepository;
import com.restaurant.it210session14bai3.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private OrderRepository orderRepo;

    @Transactional
    public void buyProduct(Long productId) {
        try {
            Product product = productRepo.findById(productId).orElseThrow();

            if (product.getStock() <= 0) {
                throw new RuntimeException("Hết hàng");
            }

            product.setStock(product.getStock() - 1);

            Order order = new Order();
            order.setProduct(product);
            order.setStatus("PAID");

            orderRepo.save(order);

        } catch (ObjectOptimisticLockingFailureException e) {
            throw new RuntimeException("Hệ thống đang bận, vui lòng thử lại sau");
        }
    }
}

// Phần 1 – Phân tích & thiết kế
// 1. Input / Output
// Input:
// productId
// quantity (thường = 1)
// Output:
// Thành công → tạo Order + trừ stock
// Thất bại → “Hết hàng” hoặc “Hệ thống bận”

// 2. Giải pháp đề xuất
//Optimistic Lock (khuyên dùng)
// Thêm @Version vào Product
// Khi 2 thread cùng update:
// 1 thread thành công
// thread còn lại → OptimisticLockException
// Ưu điểm:
// Không lock DB lâu
// Scale tốt cho flash sale
