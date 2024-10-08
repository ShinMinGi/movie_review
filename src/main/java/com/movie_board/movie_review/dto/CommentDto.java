package com.movie_board.movie_review.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDto {
    private Long id;
    private Long reviewId;  // 리뷰게시판 Id 외래키
    private Long movieId; //영화 Id 외래키
    private Long userId; //사용자 Id 외래키
    private String userName;
    private String content;
    private Long parentId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean editable; // 추가된 필드

}
