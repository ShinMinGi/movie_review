package com.movie_board.movie_review.controller;

import com.movie_board.movie_review.dto.OrderDto;
import com.movie_board.movie_review.dto.UserDto;
import com.movie_board.movie_review.service.OrderService;
import com.movie_board.movie_review.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import java.security.Principal;

@Log4j2
@Controller
@RequiredArgsConstructor
public class PaymentController {

    private final OrderService orderService;
    private final UserService userService;

    // 스토어
    @GetMapping("/store")
    public String store(Model model, Principal principal) {
        if (principal != null) {
            // 현재 로그인한 사용자의 정보를 가져옴
            UserDto user = userService.findByUsername(principal.getName());
//            log.info("username = {}", user.getId());
            model.addAttribute("user", user); // 모델에 사용자 정보를 추가
        } else {
            model.addAttribute("user", null); // 사용자 정보가 없을 경우
        }
        return "store"; // store.html 반환
    }


    @PostMapping("/payment/kg")
    public ResponseEntity<String> processKgPayment(@RequestBody OrderDto orderDto) {
        try {
            // userId가 null이 아닌지 확인
            if (orderDto.getUserId() == null) {
                return ResponseEntity.badRequest().body("{\"success\": false, \"message\": \"User ID is required.\"}");
            }
            log.info("Received payment request for userId: {}", orderDto.getUserId());
            // 결제 요청 처리
            orderService.processOrder(orderDto);

            return ResponseEntity.ok("{\"success\": true}");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("{\"success\": false}");
        }
    }

    @PostMapping("/payment/ka_request")
    public ResponseEntity<String> processKaKaoPayment(@RequestBody OrderDto orderDto) {
        try {
            log.info("Received payment request for userId: {}", orderDto.getUserId());
            orderService.processOrder(orderDto);
            return ResponseEntity.ok("{\"success\": true}");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("{\"success\": false}");
        }
    }
}
