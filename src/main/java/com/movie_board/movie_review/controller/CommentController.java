package com.movie_board.movie_review.controller;

import com.movie_board.movie_review.dto.CommentDto;
import com.movie_board.movie_review.dto.ReviewBoardDto;
import com.movie_board.movie_review.service.CommentService;
import com.movie_board.movie_review.service.ReviewBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;
    private final ReviewBoardService reviewBoardService;



    // 댓글 등록
    @PostMapping("/register")
    public ResponseEntity<?> registerComment(
            @RequestBody CommentDto commentDto,
            @RequestParam Long reviewId, // reviewId를 요청 파라미터로 받아옵니다.
            Principal principal) {
        String loggedInUserName = principal.getName();
        commentDto.setUserName(loggedInUserName);

        log.info("---------------------------------Received Comment DTO: {}", commentDto);
        log.info("@@@@@@@@@@@@Review ID: {}", reviewId); // reviewId 사용

        try {
            // reviewId로 리뷰 정보를 가져오는 로직 추가
//            ReviewBoardDto review = reviewBoardService.getReviewById(reviewId);
            Long commentId = commentService.registerComment(commentDto);
            return new ResponseEntity<>(commentId, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    // 댓글 조회
    @GetMapping("/getComments/{reviewId}")
    public ResponseEntity<List<CommentDto>> getComments(@PathVariable Long reviewId) {
        log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@reviewId = {}" + reviewId);
        List<CommentDto> comments = commentService.getCommentsByReviewId(reviewId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }


}
