package com.movie_board.movie_review.dto;

import lombok.Data;
import org.springframework.web.ErrorResponse;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ReviewBoardDto {
    private Long id;
    private String title;
    private String body;
    private String writer;
    private LocalDateTime inserted;

    private int movieId; // 추가
    private int commentCount; // 댓글 수
    private List<CommentDto> comments; // 해당 리뷰에 대한 댓글 리스트
}
