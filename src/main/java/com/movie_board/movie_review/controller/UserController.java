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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class UserController {


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

    // 이메일 입력 폼
    @GetMapping("/find/email")
    public String findEmailForm() {
        return "find_email_form";
    }

    // 이메일을 통한 비밀번호 재설정 요청 처리
    @PostMapping("/find/email")
    public String resetPassword(@RequestParam String email, RedirectAttributes redirectAttributes) {
        try {
            // 비밀번호 재설정 서비스 호출
            userService.resetUserPassword(email);
            redirectAttributes.addFlashAttribute("message", "임시 비밀번호가 이메일로 전송되었습니다.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", "해당 이메일을 가진 사용자가 없습니다.");
        }
        return "redirect:/find/email";
    }
}