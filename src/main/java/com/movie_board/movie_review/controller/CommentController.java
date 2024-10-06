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
            Principal principal) {
        log.info("댓글 등록 요청이 들어왔습니다: {}", commentDto); // 요청 로그 추가
        String loggedInUserName = principal.getName();
        commentDto.setUserName(loggedInUserName);
//
//        if (commentDto.getReviewId() == 0 || commentDto.getMovieId() == 0) {
//            return new ResponseEntity<>("리뷰 ID 또는 영화 ID가 설정되지 않았습니다.", HttpStatus.BAD_REQUEST);
//        }
        log.info("---------------------------------Received Comment DTO: {}", commentDto);
        log.info("Received Review ID: {}, Movie ID: {}", commentDto.getReviewId(), commentDto.getMovieId());
        try {
            CommentDto savedComment = commentService.registerComment(commentDto);
            return new ResponseEntity<>(savedComment, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    // 댓글 조회
    @GetMapping("/getComments/{movieId}/{reviewId}")
    public ResponseEntity<List<CommentDto>> getComments(@PathVariable int movieId, @PathVariable int reviewId) {
        log.info("@@@@@@@@@@@@@@@@@@@@@@@@movieId: {}, reviewId: {}", movieId, reviewId);
        List<CommentDto> comments = commentService.getCommentsByReviewId(movieId, reviewId);
        log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@comments : {}", comments);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }


}
