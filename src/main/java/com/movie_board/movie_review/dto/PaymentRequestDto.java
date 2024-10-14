package com.movie_board.movie_review.dto;


import lombok.Data;

@Data
public class PaymentRequestDto {
    private String userId;
    private double amount;
    private String orderId;

}
