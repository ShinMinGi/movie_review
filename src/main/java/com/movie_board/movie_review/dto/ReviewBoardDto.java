package com.movie_board.movie_review.dto;

import lombok.Data;
import org.springframework.web.ErrorResponse;

import java.time.LocalDateTime;

@Data
public class ReviewBoardDto {
    private Long id;
    private String title;
    private String body;
    private String writer;
    private LocalDateTime inserted;

    private int movieId; // 추가
}
