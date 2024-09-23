package com.movie_board.movie_review.service;


import com.movie_board.movie_review.dto.OrderDto;
import com.movie_board.movie_review.dto.UserDto;
import com.movie_board.movie_review.repository.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderMapper orderMapper;
    private final UserService userService;


    @Transactional
    public void processOrder(OrderDto orderDto) {
        // 결제 전 기본 주문 정보 저장 (결제 진행 중 상태로 저장)
        orderDto.setStatus("PENDING");
        orderMapper.insertOrder(orderDto);

        // 결제 성공/실패 여부는 외부 API 처리 후 상태 업데이트
        // 예시: 외부 결제 API 호출 후, 상태 변경 로직 추가 가능
    }
}
