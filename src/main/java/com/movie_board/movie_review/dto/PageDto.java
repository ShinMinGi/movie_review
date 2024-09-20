package com.movie_board.movie_review.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PageDto{

    private int total;      //페이지 총합
    private int pageStart;  // 페이지 시작
    private int pageEnd;    // 페이지 끝
    private boolean nextPage; // 다음 페이지
    private boolean prevPage; // 이전 페이지
    private int currentPage; // 현재 페이지
    private int pageSize; // 한 페이지당 게시글 수

    private String searchKeyword; // 서칭 조건
    private String filter; // 필터링 조건



    public PageDto(int total, int currentPage, int pageSize, String searchKeyword, String filter) {
        this.total = total;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.searchKeyword = searchKeyword;
        this.filter = filter;


        // 페이지 끝 번호 계산
        this.pageEnd = (int) (Math.ceil((double) currentPage/ 5.0)) * 5;

        // 페이지 시작 시 번호 계산
        this.pageStart = this.pageEnd - 4;

        // 마지막 페이지 번호 계산
        int realEnd = (int) (Math.ceil((total * 1.0) / pageSize));

    // 페이지 끝 번호가 실제 마지막 페이지보다 큰 경우 실제 마지막 페이지로 설정
        if(pageEnd > realEnd) {
            this.pageEnd = pageEnd;
    }
    // 이전 페이지 존재여부
        this.prevPage = this.pageStart > 1;

    // 다음 페이지 존재여부
        this.nextPage = this.pageEnd < realEnd;

    }
}