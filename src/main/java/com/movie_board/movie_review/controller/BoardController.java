package com.movie_board.movie_review.controller;



import com.movie_board.movie_review.dto.ReviewBoardDto;

import com.movie_board.movie_review.service.ReviewBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping({"/","MovieReview"})
    public String mvreview() {
        return "mv_review";
    }

    // Read All (GET 요청으로 모든 리뷰 조회)
    @GetMapping("/movie/board")
    public String getAllReviewss(Model model) {
        List<ReviewBoardDto> reviews = reviewBoardService.findAllReviews();
        model.addAttribute("reviewList", reviews);
//        log.info("reviewList = {}",reviews);
        return "mv_review_board";  // mv_review_board.html 파일로 연결
    }

    // 글쓰기 등록/삭제/수정 페이지 화면
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
        log.info("createReview={}",review);
        // 데이터를 처리한 후 다시 mv_review_board로 리다이렉트
        return "redirect:/movie/board";
    }

    // 게시글 상세 보기 Read
    @GetMapping("/movie/read")
    public String read(@RequestParam("id") Long id, Model model) {
        ReviewBoardDto review = reviewBoardService.selectOne(id);
        model.addAttribute("review", review); // "review"라는 이름으로 모델에 추가
        return "mv_review_board_detail"; // 해당 HTML 파일을 렌더링
    }

    // 게시글 삭제
    @PostMapping("/movie/board/remove/{id}")
    public String remove(@PathVariable Long id, RedirectAttributes redirectAttributes){
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



    // Create (POST 요청으로 새 리뷰 생성)
//    @PostMapping("/create")
//    public ResponseEntity<Void> createReview(@RequestBody ReviewBoardDto review) {
//        reviewBoardService.createReview(review);
//        return ResponseEntity.status(HttpStatus.CREATED).build();
//    }


//
//    // Read By ID (GET 요청으로 특정 리뷰 조회)
//    @GetMapping("/movie/board/{id}")
//    public ResponseEntity<ReviewBoardDto> getReviewById(@PathVariable Long id) {
//        ReviewBoardDto review = reviewBoardService.getReviewById(id);
//        return ResponseEntity.ok(review);
//    }
//


}