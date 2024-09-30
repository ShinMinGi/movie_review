package com.movie_board.movie_review.service;


import com.movie_board.movie_review.dto.OrderDto;
import com.movie_board.movie_review.dto.UserDto;
import com.movie_board.movie_review.repository.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderMapper orderMapper;
    private final UserService userService;

    public void createOrder(OrderDto orderDto) {
        orderMapper.insertOrder(orderDto);
    }

    // 주문 생성
    public void saveOrder(OrderDto orderDto) {
        orderMapper.insertOrder(orderDto);
    }

    // 주문 ID로 주문 정보 조회 메서드
    // 주문 ID로 주문 조회
    public OrderDto findByOrderId(String orderId) {
        // orderMapper를 통해 orderId에 해당하는 주문 정보를 가져옴
        return orderMapper.findByOrderId(orderId);
    }

}
