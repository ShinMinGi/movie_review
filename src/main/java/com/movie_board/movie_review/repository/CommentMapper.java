package com.movie_board.movie_review.repository;

import com.movie_board.movie_review.dto.CommentDto;
import com.movie_board.movie_review.dto.ReviewBoardDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper {


    // 댓글 등록
    void insertComment(CommentDto commentDto);


    // 댓글 목록 ㄹ요청
    List<CommentDto> selectCommentsByReviewId(Long reviewId);
}
