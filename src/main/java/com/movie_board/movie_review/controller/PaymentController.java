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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Map;
import java.util.UUID;

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


//     store.html에서 수량 선택 후 orderorm.html로 이동
    @GetMapping("/store/form")
    public String showOrderForm (@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return "redirect:/login";
        }

        return "orderForm"; // orderForm.html 템플릿
    }


    @PostMapping("/store/form")
    public String processOrder(OrderDto orderDto, RedirectAttributes redirectAttributes) {
        int unitPrice = 7000; // 개당 가격
        int deliveryFee = 3000; // 배송비
        int totalAmount = (unitPrice * orderDto.getQuantity()) + deliveryFee;

        orderDto.setOrderId(UUID.randomUUID().toString());
        orderDto.setAmount(totalAmount);
        orderDto.setDeliveryFee(deliveryFee);

        // 리다이렉트 시 orderDto 추가
        redirectAttributes.addFlashAttribute("orderDto", orderDto);
        redirectAttributes.addFlashAttribute("quantity", orderDto.getQuantity());
        redirectAttributes.addFlashAttribute("amount", totalAmount);

        return "redirect:/store/form"; // 주문 상세 페이지로 리다이렉트
    }



    //        결제지불 할 시 post 방식으로 전송
    @PostMapping("/payment/kg")
    public String kgPay() {
        return "redirect:/store/order/details";
    }

    @PostMapping("/payment/ka")
    public String kaPay(OrderDto orderDto, RedirectAttributes redirectAttributes) {

        orderService.createOrder(orderDto);
        return "redirect:/store/order/details";
    }







    // 주문 상세 페이지 표시
    @GetMapping("/store/order/details")
    public String orderDetails(@ModelAttribute("orderDto") OrderDto orderDto, Model model) {
        // orderDto가 null일 경우 새 객체 생성
        if (orderDto == null) {
            orderDto = new OrderDto();
        }
        model.addAttribute("orderDto", orderDto); // 모델에 orderDto 추가
        return "orderDetails"; // orderForm.html 반환
    }


    @PostMapping("/store/order/details")
    public ResponseEntity<?> postDetails(@RequestBody OrderDto orderDto, Model model) {
        // 주문 ID 생성 로직
        orderDto.setOrderId(UUID.randomUUID().toString());

        // OrderDto를 데이터베이스에 저장
        orderService.createOrder(orderDto);

        // 주문 상세 페이지로 리다이렉트
        model.addAttribute("orderDto", orderDto);
        return ResponseEntity.ok().body(Map.of("success", true, "message", "Order saved successfully"));
    }

}


