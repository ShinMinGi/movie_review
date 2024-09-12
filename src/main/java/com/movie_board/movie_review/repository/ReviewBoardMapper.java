package com.movie_board.movie_review.repository;

import com.movie_board.movie_review.dto.ReviewBoardDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReviewBoardMapper {
    List<ReviewBoardDto> findAllReviews();

    //게시글 클릭 시 조회
    ReviewBoardDto findById(Long id);

    // 등록
    void createReview(ReviewBoardDto reviewBoardDto);

    // 삭제
    void deleteReview(Long id);

    // 수정
    int updateReview(ReviewBoardDto reviewBoardDto);
}