package com.movie_board.movie_review.repository;

import com.movie_board.movie_review.dto.OrderDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface OrderMapper {
    void insertOrder(OrderDto orderDto);

    // 주문 ID로 주문 정보 가져오는 메서드
    OrderDto findByOrderId(@Param("orderId") String orderId);

}