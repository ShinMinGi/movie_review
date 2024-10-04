package com.movie_board.movie_review.controller;

import com.movie_board.movie_review.dto.CommentDto;
import com.movie_board.movie_review.dto.ReviewBoardDto;
import com.movie_board.movie_review.service.CommentService;
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

    // 댓글 등록
    @PostMapping("/register")
    public ResponseEntity<?> registerComment(@RequestBody CommentDto commentDto, Principal principal) {
        // 로그인한 사용자의 이름을 가져옴
        String loggedInUserName = principal.getName();
        commentDto.setUserName(loggedInUserName);

        // 로그 추가
        System.out.println("---------------------------------Received Comment DTO: " + commentDto);
        System.out.println("Review ID: " + commentDto.getReviewId()); // reviewId 로그 추가

        Long commentId = commentService.registerComment(commentDto);
        return new ResponseEntity<>(commentId, HttpStatus.CREATED);
    }



    // 댓글 조회
    @GetMapping("/review/{reviewId}")
    public ResponseEntity<List<CommentDto>> getComments(@PathVariable int reviewId) {
        log.info("reviewId = {}" + reviewId);
        List<CommentDto> comments = commentService.getCommentsByReviewId(reviewId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

//    // 댓글 수정
//    @PostMapping("/modify")
//    public ResponseEntity<?> modifyComment(@RequestBody CommentDto commentDto) {
//        commentService.modifyComment(commentDto);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//    // 댓글 삭제
//    @PostMapping("/remove/{id}")
//    public ResponseEntity<?> removeComment(@PathVariable Long id) {
//        commentService.removeComment(id);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//    @GetMapping("/detail/{id}")
//    public String getReviewDetail(@PathVariable Long id, Model model) {
//        CommentDto review = commentService.commentFindById(id);
//        model.addAttribute("review", review);
//        return "mv_review_board_detail";
//    }
}
