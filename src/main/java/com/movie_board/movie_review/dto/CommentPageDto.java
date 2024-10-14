package com.movie_board.movie_review.dto;

import lombok.Data;

import java.util.List;

@Data
public class CommentPageDto {
    private List<CommentDto> comments; // 현재 페이지의 댓글 목록
    private int total;                 // 전체 댓글 수
    private int pageStart;             // 페이지 시작 번호
    private int pageEnd;               // 페이지 끝 번호
    private boolean hasNext;           // 다음 페이지 존재 여부
    private boolean hasPrevious;       // 이전 페이지 존재 여부
    private int currentPage;           // 현재 페이지
    private int pageSize;              // 한 페이지당 댓글 수
    private int totalPages;            // 총 페이지 수

    public CommentPageDto(List<CommentDto> comments, int total, int currentPage, int pageSize) {
        this.comments = comments;
        this.total = total;
        this.currentPage = currentPage;
        this.pageSize = pageSize;

        // 총 페이지 수 계산
        this.totalPages = (int) Math.ceil((double) total / pageSize);

        // 페이지 끝 번호 계산
        this.pageEnd = (int) (Math.ceil((double) currentPage / 5.0)) * 5;

        // 페이지 시작 번호 계산
        this.pageStart = this.pageEnd - 4;

        // 마지막 페이지 번호 계산
        int realEnd = (int) (Math.ceil((total * 1.0) / pageSize));

        // 페이지 끝 번호가 실제 마지막 페이지보다 큰 경우 실제 마지막 페이지로 설정
        if (pageEnd > realEnd) {
            this.pageEnd = realEnd;
        }

        // 이전 페이지 존재 여부
        this.hasPrevious = this.pageStart > 1;

        // 다음 페이지 존재 여부
        this.hasNext = this.pageEnd < realEnd;
    }
}
