package com.movie_board.movie_review.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

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
    private boolean showDropdown; // 드롭메뉴 표시 여부 추가
    // 대댓글 리스트 추가
    private List<CommentDto> replies;
}
