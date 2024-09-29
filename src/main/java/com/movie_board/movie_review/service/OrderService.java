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


}
