package com.movie_board.movie_review.controller;

import com.movie_board.movie_review.dto.CommentDto;
import com.movie_board.movie_review.dto.CommentPageDto;
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
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    private final UserService userService;

    // 댓글 등록
    @PostMapping("/comment/add")
    public ResponseEntity<Map<String, Object>> addComment(@RequestBody CommentDto commentDto) {
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
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("success", false, "message", "유효하지 않은 사용자입니다."));
        }

        // 댓글 작성자 userId 설정
        commentDto.setUserId(userId); // CommentDto에 userId 설정
        commentDto.setUserName(username); // CommentDto에 userName 설정
        log.info("User ID set in CommentDto: " + userId);
        log.info("User Name set in CommentDto: " + username);

        // 댓글 저장 로직
        boolean isSuccess = commentService.addComment(commentDto); // 댓글 추가 성공 여부

        // 응답 생성
        Map<String, Object> response = new HashMap<>();
        response.put("success", isSuccess);
        response.put("message", isSuccess ? "댓글이 성공적으로 추가되었습니다." : "댓글 등록에 실패했습니다.");

        return ResponseEntity.ok(response); // JSON 형식으로 응답
    }



    // 댓글 목록 조회
    @GetMapping("/comment/list/{reviewId}")
    public ResponseEntity<CommentPageDto> getCommentList(
            @PathVariable Long reviewId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int pageSize,
            Principal principal) {

        log.info("getMapping 의 reviewId가 들어오는지  = " + reviewId);

        // 현재 사용자 ID 확인
        Long currentUserId = userService.getUserIdByUsername(principal.getName());
        log.info("currentUserId = {}", currentUserId);
        // 전체 댓글 수 가져오기
        int total = commentService.getCommentCountByReviewId(reviewId);

        // 페이지에 맞는 댓글 목록 가져오기 (대댓글 없이 댓글만 조회)
        List<CommentDto> comments = commentService.getCommentListWithPaging(reviewId, page, pageSize);

        // 각 댓글에 대해 드롭메뉴 표시 여부만 설정 (대댓글 로드 로직 제거)
        for (CommentDto comment : comments) {
            // 드롭메뉴 표시 여부 설정 (댓글 작성자만 표시)
            comment.setShowDropdown(comment.getUserId().equals(currentUserId));

        }

        // 페이징 정보를 포함한 CommentPageDTO 생성
        CommentPageDto commentPageDto = new CommentPageDto(comments, total, page, pageSize);
        log.info("CommentPageDto: " + commentPageDto); // 로그 추가
        return ResponseEntity.ok(commentPageDto);
    }











    // 댓글 수정
    @PostMapping("/comment/update/{commentId}")
    public ResponseEntity<?> updateComment(
            @PathVariable("commentId") Long commentId,
            @RequestBody Map<String, String> body) { // JSON 요청을 받을 수 있도록 수정
        String content = body.get("content"); // content를 가져옵니다.

        // 현재 사용자 ID 확인
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        Long userId = userService.getUserIdByUsername(username);

        // 댓글 작성자인지 확인
        if (!commentService.isCommentOwner(commentId, userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("댓글 작성자만 수정할 수 있습니다.");
        }

        // 댓글 수정 처리
        commentService.updateComment(commentId, content, userId); // 이 부분이 데이터베이스에 업데이트가 이루어져야 합니다.
        return ResponseEntity.ok("댓글 수정이 완료되었습니다");
    }



    // 댓글 삭제
    @PostMapping("/comment/delete/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable("commentId") Long commentId) {

        // 현재 사용자 ID 확인
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        Long userId = userService.getUserIdByUsername(username);

        // 댓글 작성자인지 확인
        if (!commentService.isCommentOwner(commentId, userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("댓글 작성자만 수정할 수 있습니다.");
        }
        commentService.deleteComment(commentId);
        return ResponseEntity.ok("댓글이 삭제되었습니다");
    }

    // 대댓글 등록
    @PostMapping("/comment/reply/add")
    public ResponseEntity<Map<String, Object>> addReply(@RequestBody CommentDto commentDto) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        Long userId = userService.getUserIdByUsername(username);
        if (userId == null) {
            log.warn("Invalid user: {}", username);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("success", false, "message", "유효하지 않은 사용자입니다."));
        }

        commentDto.setUserId(userId);
        commentDto.setUserName(username);
        log.info("User ID set in CommentDto: {}", userId);
        log.info("User Name set in CommentDto: {}", username);

        boolean isSuccess = false;
        try {
            isSuccess = commentService.addReply(commentDto);
        } catch (Exception e) {
            log.error("Error adding reply: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("success", false, "message", "대댓글 등록 중 오류가 발생했습니다."));
        }

        log.info("Reply added successfully: {}", isSuccess);
        Map<String, Object> response = new HashMap<>();
        response.put("success", isSuccess);
        response.put("message", isSuccess ? "대댓글이 성공적으로 추가되었습니다." : "대댓글 등록에 실패했습니다.");

        return ResponseEntity.ok(response);
    }



    // 대댓글 목록 조회
    @GetMapping("/comment/replies/{parentId}")
    public ResponseEntity<List<CommentDto>> getReplies(@PathVariable Long parentId, Principal principal, Model model) {
        List<CommentDto> replies = commentService.getRepliesByParentId(parentId);


        if (principal == null) {
            log.warn("Principal is null");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 인증되지 않은 사용자 처리
        }

        Long currentUserId = userService.getUserIdByUsername(principal.getName());
        log.info("currentUserId = {}", currentUserId);
        if (currentUserId == null) {
            log.warn("Current user ID is null");
        }

        // 대댓글 작성자인지 여부 설정
        for (CommentDto reply : replies) {
            reply.setShowDropdown(reply.getUserId().equals(currentUserId));
            log.info("Reply ID: {}, User ID: {}, Show Dropdown: {}", reply.getId(), reply.getUserId(), reply.isShowDropdown());
        }

        return new ResponseEntity<>(replies, HttpStatus.OK);
    }




    // 대댓글 수정
    @PostMapping("/comment/replyUpdate/{replyId}")
    public ResponseEntity<?> updateReply(
            @PathVariable("replyId") Long replyId,
            @RequestBody Map<String, String> body) {
        String content = body.get("content");

        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        Long userId = userService.getUserIdByUsername(username);

        if (!commentService.isReplyOwner(replyId, userId)) {
            log.warn("User {} is not the owner of reply {}", userId, replyId);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("대댓글 작성자만 수정할 수 있습니다.");
        }

        try {
            commentService.updateReply(replyId, content, userId);
            log.info("Reply {} updated successfully by user {}", replyId, userId);
        } catch (Exception e) {
            log.error("Error updating reply: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("대댓글 수정 중 오류가 발생했습니다.");
        }

        return ResponseEntity.ok("대댓글 수정이 완료되었습니다.");
    }


    // 대댓글 삭제
    @PostMapping("/comment/replyDelete/{replyId}")
    public ResponseEntity<?> deleteReply(@PathVariable("replyId") Long replyId) {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        Long userId = userService.getUserIdByUsername(username);

        if (!commentService.isReplyOwner(replyId, userId)) {
            log.warn("User {} is not the owner of reply {}", userId, replyId);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("대댓글 작성자만 삭제할 수 있습니다.");
        }

        try {
            commentService.deleteReply(replyId, userId);
            log.info("Reply {} deleted successfully by user {}", replyId, userId);
        } catch (Exception e) {
            log.error("Error deleting reply: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("대댓글 삭제 중 오류가 발생했습니다.");
        }

        return ResponseEntity.ok("대댓글이 삭제되었습니다.");
    }




}