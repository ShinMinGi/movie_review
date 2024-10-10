package com.movie_board.movie_review.controller;

import com.movie_board.movie_review.dto.CommentDto;
import com.movie_board.movie_review.dto.ReviewBoardDto;
import com.movie_board.movie_review.service.CommentService;
import com.movie_board.movie_review.service.ReviewBoardService;
import com.movie_board.movie_review.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    private final UserService userService;

    // 댓글 등록
    @PostMapping("/comment/add")
    public ResponseEntity<?> addComment(@RequestBody CommentDto commentDto) {
        // SecurityContext에서 username 가져오기
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        // username을 통해 userId 조회 (UserService에서 처리)
        Long userId = userService.getUserIdByUsername(username);
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("유효하지 않은 사용자입니다.");
        }

        // 댓글 작성자 userId 설정
        commentDto.setUserId(userId); // CommentDto에 userId 설정
        commentDto.setUserName(username); // CommentDto에 userName 설정
        log.info("User ID set in CommentDto: " + userId);
        log.info("User Name set in CommentDto: " + username);

        // 댓글 저장 로직
        commentService.addComment(commentDto); // 실제 댓글을 추가하는 서비스 호출
        log.info("Comment successfully added.");

        return ResponseEntity.ok("댓글이 성공적으로 추가되었습니다.");
    }



    // 댓글 목록 조회
    @GetMapping("/comment/list/{reviewId}")
    public ResponseEntity<List<CommentDto>> getCommentList(@PathVariable Long reviewId) {
        log.info("GETMAPPING에서 의 reviewId가 들어오는지  = "+ reviewId);
        List<CommentDto> comments = commentService.getCommentList(reviewId);
        return ResponseEntity.ok(comments);
    }


}
