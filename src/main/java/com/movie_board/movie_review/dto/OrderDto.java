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

    // 모든 필드를 포함하는 생성자
//    public OrderDto(Long userId, String orderId, int amount, String status,
//                    LocalDateTime createdAt, int quantity, int deliveryFee, String deliveryAddress, String phoneNumber) {
//        this.userId = userId;
//        this.orderId = orderId;
//        this.amount = amount;
//        this.status = status;
//        this.createdAt = createdAt;
//        this.quantity = quantity;
//        this.deliveryFee = deliveryFee;
//        this.deliveryAddress = deliveryAddress;
//        this.phoneNumber = phoneNumber;
//    }

    // 계산 메서드 (필요한 경우)
    private int calculateTotalPrice(int amount, int quantity, int deliveryFee) {
        return (amount * quantity) + deliveryFee;
    }

}
