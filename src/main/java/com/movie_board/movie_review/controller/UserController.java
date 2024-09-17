package com.movie_board.movie_review.controller;

import com.movie_board.movie_review.dto.UserDto;
import com.movie_board.movie_review.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
@Controller
@RequiredArgsConstructor

public class UserController {
    @Autowired
    private final UserService userService;

    @GetMapping("/signup")
    public String showSignUpForm(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "sign_up";  // signup.html 반환
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute UserDto userDto) {
        userService.registerUser(userDto);
        return "redirect:/login";
    }
}


    // 회원가입 처리
//    @PostMapping("/signup")
//    public String signUp(@ModelAttribute UserDto userDto, Model model) {
//        try {
//            userService.signUp(userDto);
//            return "redirect:/login";  // 회원가입 성공 시 로그인 페이지로 리다이렉트
//        } catch (IllegalArgumentException e) {
//            model.addAttribute("errorMessage", e.getMessage());
//            return "sign_up";  // 회원가입 실패 시 에러 메시지와 함께 폼 재렌더링
//        }
//    }

