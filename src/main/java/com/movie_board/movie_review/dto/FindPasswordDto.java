package com.movie_board.movie_review.dto;

import lombok.Data;

@Data
public class FindPasswordDto {
    private String email;
    private String newPassword;
}
