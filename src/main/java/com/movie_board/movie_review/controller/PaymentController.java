package com.movie_board.movie_review.controller;

import com.movie_board.movie_review.dto.OrderDto;
import com.movie_board.movie_review.dto.UserDto;
import com.movie_board.movie_review.service.OrderService;
import com.movie_board.movie_review.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.UUID;

@Log4j2
@Controller
@RequiredArgsConstructor
public class PaymentController {

    private final OrderService orderService;
    private final UserService userService;

    // 스토어
    // 스토어
    @GetMapping("/store")
    public String store(Model model, Principal principal) {
        if (principal != null) {
            // 현재 로그인한 사용자의 정보를 가져옴
            UserDto user = userService.findByUsername(principal.getName());
            model.addAttribute("user", user); // 모델에 사용자 정보를 추가
        } else {
            model.addAttribute("user", null); // 사용자 정보가 없을 경우
        }

        // OrderDto 객체를 생성하여 모델에 추가
        OrderDto orderDto = new OrderDto();
        orderDto.setQuantity(1); // 기본 수량을 1로 설정
        model.addAttribute("orderDto", orderDto);

        return "store"; // store.html 반환
    }


    // store.html에서 수량 선택 후 orderForm.html로 이동
    @GetMapping("/store/form")
    public String showOrderForm () {
//        model.addAttribute("quantity",quantity);
//        model.addAttribute("amount", amount);
        return "orderForm"; // orderForm.html 템플릿
    }


    // 사용자가 orderForm.html에서 결제 정보를 제출하면 처리하는 메서드
    @PostMapping("/processOrder")
    public String processOrder(@ModelAttribute OrderDto orderDto,
                               @RequestParam("quantity") int quantity) {

        // 고유한 orderId 생성
        orderDto.setOrderId(UUID.randomUUID().toString());
        orderDto.setQuantity(quantity); // quantity를 orderDto에 설정
        // 주문 처리 로직
        orderService.createOrder(orderDto);

        return "redirect:/store/form";
    }



    // 주문 완료 페이지
//    @GetMapping("/orderConfirmation")
//    public String showOrderConfirmation() {
//        return "orderConfirmation"; // 주문 완료 페이지
//    }
//


}
