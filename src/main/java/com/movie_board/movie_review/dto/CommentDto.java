package com.movie_board.movie_review.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDto {
    private Long id;
    private int reviewId;
    private int userId;
    private String userName;
    private String content;
    private Long parentId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean editable; // 추가된 필드

}
