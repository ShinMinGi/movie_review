package com.movie_board.movie_review.repository;

import com.movie_board.movie_review.dto.OrderDto;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface OrderMapper {
    void insertOrder(OrderDto orderDto);
}