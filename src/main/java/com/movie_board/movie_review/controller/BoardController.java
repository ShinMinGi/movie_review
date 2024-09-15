package com.movie_board.movie_review.controller;


import com.movie_board.movie_review.dto.PageDto;
import com.movie_board.movie_review.dto.ReviewBoardDto;

import com.movie_board.movie_review.service.ReviewBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BoardController {

    @Autowired
    private ReviewBoardService reviewBoardService;

    @GetMapping({"/", "MovieReview"})
    public String mvreview() {
        return "mv_review";
    }


    // Read All (GET 요청으로 모든 리뷰 조회)  ,  페이징 기능도 동시에 구현
    @GetMapping("/movie/board")
    public String getAllReviews(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(value = "searchKeyword", defaultValue = "") String searchKeyword,
            @RequestParam(value = "filter", defaultValue = "") String filter,
            Model model) {

        // 전체 게시글 수 가져오기
        int totalReviews = reviewBoardService.getTotalReviews(searchKeyword, filter);

        PageDto pageDto = new PageDto(totalReviews, page, pageSize, searchKeyword, filter);
        // 현재 페이지의 게시글 목록 가져오기
        List<ReviewBoardDto> reviews = reviewBoardService.getPagedList(page, pageSize, searchKeyword,filter);

        // PageDto를 이용한 페이징 처리


        // 모델에 데이터 추가
        model.addAttribute("reviewList", reviews);
        model.addAttribute("pageDto", pageDto);

        return "mv_review_board";  // mv_review_board.html 파일로 연결
    }


    // 글쓰기 등록/삭제/수정 페이지 화면
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/board/write")
    public String write() {
        return "write";
    }

    // 글쓰기 등록 구현
    @PostMapping("/movie/board")
    public String createReview(@RequestParam String writer,
                               @RequestParam String title,
                               @RequestParam String body) {
        ReviewBoardDto review = new ReviewBoardDto();
        review.setWriter(writer);
        review.setTitle(title);
        review.setBody(body);

        reviewBoardService.createReview(review);
        // 폼 데이터를 처리하는 로직 (예: 데이터베이스에 저장)
        log.info("createReview={}", review);
        // 데이터를 처리한 후 다시 mv_review_board로 리다이렉트
        return "redirect:/movie/board";
    }

    // 게시글 상세 보기 Read
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/movie/read")
    public String read(@RequestParam("id") Long id, Model model) {
        ReviewBoardDto review = reviewBoardService.selectOne(id);
        model.addAttribute("review", review); // "review"라는 이름으로 모델에 추가
        return "mv_review_board_detail"; // 해당 HTML 파일을 렌더링
    }

    // 게시글 삭제
    @PostMapping("/movie/board/remove/{id}")
    public String remove(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        reviewBoardService.deleteReview(id);
        log.info("-------------------------remove------------------------------");
        log.info("id : " + id);
        return "redirect:/movie/board";
    }

    // 게시글 수정 edit
    @GetMapping("/edit/{id}")
    public String editReview(@PathVariable Long id, Model model) {
        ReviewBoardDto review = reviewBoardService.selectOne(id); // 리뷰 조회
        model.addAttribute("review", review); // 모델에 리뷰 추가
        return "edit"; // 수정 폼 템플릿
    }

    @PostMapping("/edit")
    public String updateReview(@ModelAttribute ReviewBoardDto reviewBoardDto) {
        reviewBoardService.editReview(reviewBoardDto);
        return "redirect:/movie/read?id=" + reviewBoardDto.getId();
    }


    @GetMapping("event")
    public String event() {
        return "event";
    }


    @GetMapping("event2")
    public String event2() {
        return "event2";
    }



    // login page
    @GetMapping("/login")
    public String loginGET(String errorCode, String logout) {
        log.info("login get..........................");
        log.info("logout: " + logout);

        if (logout != null) {
            log.info("user logout...................................");
        }
        return "login"; // login.html 파일을 반환
    }



    @GetMapping("/sign_up")
    public String signUp() {
        return "sign_up";
    }
}