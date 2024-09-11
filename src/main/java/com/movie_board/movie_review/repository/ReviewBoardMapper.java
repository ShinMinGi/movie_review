package com.movie_board.movie_review.repository;

import com.movie_board.movie_review.dto.ReviewBoardDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReviewBoardMapper {
    List<ReviewBoardDto> getAllReviews();

}