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

    private int quantity;
    private int deliveryFee;
    private String deliveryAddress; // 추가된 배송 주소 필드
    private String phoneNumber; // 추가된 핸드폰 번호 필드
    private String orderName;
    private String orderEmail;

}
