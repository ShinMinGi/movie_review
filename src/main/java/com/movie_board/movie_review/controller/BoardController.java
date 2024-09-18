package com.movie_board.movie_review.controller;


import com.movie_board.movie_review.dto.PageDto;
import com.movie_board.movie_review.dto.ReviewBoardDto;
import com.movie_board.movie_review.dto.UserDto;
import com.movie_board.movie_review.service.ReviewBoardService;
import com.movie_board.movie_review.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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
    @Autowired
    private UserService userService;
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

        int totalReviews = reviewBoardService.getTotalReviews(searchKeyword, filter);
        PageDto pageDto = new PageDto(totalReviews, page, pageSize, searchKeyword, filter);

        // 페이지에 맞는 리뷰 가져오기
        List<ReviewBoardDto> reviews = reviewBoardService.getPagedList(page, pageSize, searchKeyword, filter);

        // 모델에 추가
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

//    // 로그인 된 정보 세션을 이용한 게시글 작성
//    @PostMapping("write")
//    public String writeBoard(ReviewBoardDto reviewBoardDto, HttpSession session) {
//        UserDto loggedInUser = (UserDto) session.getAttribute("user");
//
//        if (loggedInUser == null) {
//            return "redirect:/login";
//        }
//        reviewBoardDto.setWriter(loggedInUser.getUserName());
//
//        reviewBoardService.createReview(reviewBoardDto);
//        return "redirect:/movie/board";
//    }

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
    @GetMapping("/movie/read/{id}")
    public String read(@PathVariable Long id, Model model) {
        ReviewBoardDto review = reviewBoardService.selectOne(id);
        model.addAttribute("review", review);
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

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login?logout"; // 로그아웃 후 로그인 페이지로 리다이렉트
    }

    // 영화의 리뷰 목록을 가져오는 메서드
    @GetMapping("/movie/review/{movieId}")
    public String getReviewsByMovieId(@PathVariable Long movieId, Model model) {
        System.out.println("Received movieId: " + movieId); // movieId가 제대로 전달되는지 확인
        List<ReviewBoardDto> reviews = reviewBoardService.getReviewsByMovieId(movieId);
        if (reviews == null || reviews.isEmpty()) {
            System.out.println("No reviews found for movieId: " + movieId); // 리뷰가 없을 때 처리
        }
        model.addAttribute("reviewList", reviews);
        return "mv_review_board"; // HTML 파일 이름과 일치
    }

}