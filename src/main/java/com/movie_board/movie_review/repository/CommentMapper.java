package com.movie_board.movie_review.repository;

import com.movie_board.movie_review.dto.CommentDto;
import com.movie_board.movie_review.dto.ReviewBoardDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper {

    void commentRegister(CommentDto commentDto); // 댓글 등록

    List<CommentDto> getCommentsByReviewId(@Param("reviewId") Long reviewId); // 리뷰 ID로 댓글 조회

}
