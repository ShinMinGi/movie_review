package com.movie_board.movie_review.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderDto {
    private Long id;
    private Long userId;
    private String orderId;
    private int amount;
    private String status;
    private LocalDateTime createdAt;
}
