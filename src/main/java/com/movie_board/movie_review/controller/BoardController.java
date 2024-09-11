package com.movie_board.movie_review.controller;



import com.movie_board.movie_review.dto.ReviewBoardDto;

import com.movie_board.movie_review.service.ReviewBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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


//    @GetMapping("/movie/board")
//    public String list(Model model) {
//        return "mv_review_board";
//    }
    @PostMapping("/movie/board")
    public String submitForm(ReviewBoardDto reviewBoardDto) {
        // 폼 데이터를 처리하는 로직 (예: 데이터베이스에 저장)

        // 데이터를 처리한 후 다시 mv_review_board로 리다이렉트
        return "redirect:/movie/board";
    }


    @GetMapping("event")
    public String event() {
        return "event";
    }


    @GetMapping("event2")
    public String event2() {
        return "event2";
    }

    @GetMapping("/write")
    public String write() {
        return "write";
    }


    // Create (POST 요청으로 새 리뷰 생성)
//    @PostMapping("/create")
//    public ResponseEntity<Void> createReview(@RequestBody ReviewBoardDto review) {
//        reviewBoardService.createReview(review);
//        return ResponseEntity.status(HttpStatus.CREATED).build();
//    }

    // Read All (GET 요청으로 모든 리뷰 조회)
    @GetMapping("/movie/board")
    public String getAllReviewss(Model model) {
        List<ReviewBoardDto> reviews = reviewBoardService.getAllReviews();
        model.addAttribute("reviewList", reviews);
        return "mv_review_board";  // mv_review_board.html 파일로 연결
    }
//
//    // Read By ID (GET 요청으로 특정 리뷰 조회)
//    @GetMapping("/movie/board/{id}")
//    public ResponseEntity<ReviewBoardDto> getReviewById(@PathVariable Long id) {
//        ReviewBoardDto review = reviewBoardService.getReviewById(id);
//        return ResponseEntity.ok(review);
//    }
//
//    // Update (POST 요청으로 리뷰 업데이트)
//    @PostMapping("/update/{id}")
//    public ResponseEntity<Void> updateReview(@PathVariable Long id, @RequestBody ReviewBoardDto review) {
//        review.setId(id);
//        reviewBoardService.updateReview(review);
//        return ResponseEntity.ok().build();
//    }
//
//    // Delete (POST 요청으로 리뷰 삭제)
//    @PostMapping("/delete/{id}")
//    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
//        reviewBoardService.deleteReview(id);
//        return ResponseEntity.noContent().build();
//    }
}