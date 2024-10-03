package com.movie_board.movie_review.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDto {
    private int id;
    private int userId;
    private Integer parentId;
    private String content;
    private LocalDateTime createdAt;
    private int depth;
    private int orderInGroup;

}
