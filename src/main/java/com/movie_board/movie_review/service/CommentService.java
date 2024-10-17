package com.movie_board.movie_review.service;

import com.movie_board.movie_review.dto.CommentDto;
import com.movie_board.movie_review.dto.CommentPageDto;
import com.movie_board.movie_review.dto.ReviewBoardDto;
import com.movie_board.movie_review.repository.CommentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class CommentService {

    private final CommentMapper commentMapper;


    // 댓글 등록
    public boolean addComment(CommentDto commentDto) {
        // userName이 null인지 확인
        if (commentDto.getUserName() == null) {
            throw new IllegalArgumentException("userName must not be null");
        }

        // 댓글 생성 시간 설정
        commentDto.setCreatedAt(LocalDateTime.now());

        try {
            // 댓글을 DB에 추가
            commentMapper.insertComment(commentDto);
            log.info("Comment successfully added: {}", commentDto);
            return true;  // 성공 시 true 반환
        } catch (Exception e) {
            log.error("Error adding comment: {}", e.getMessage());
            return false;  // 실패 시 false 반환
        }
    }



    // 댓글 목록 조회 시 대댓글을 포함하지 않도록 수정
    public List<CommentDto> getCommentsWithReplies(Long reviewId) {
        List<CommentDto> comments = commentMapper.selectCommentsByReviewId(reviewId);
        // 대댓글 조회는 클라이언트 요청 시에만 수행하도록 변경
        return comments;
    }

    // 대댓글 저장 로직
    public boolean addReply(CommentDto commentDto) {
        commentMapper.insertReply(commentDto);
        return true;
    }
    // DB에서 대댓글 목록 조회 로직 구현
    public List<CommentDto> getRepliesByParentId(Long parentId) {
        return commentMapper.selectRepliesByParentId(parentId);
    }



    // 댓글 수정
    public void updateComment(Long commentId, String content, Long userId) {
        commentMapper.updateComment(commentId, content, userId);
    }



    // 댓글 삭제
    public void deleteComment(Long commentId) {
        commentMapper.deleteComment(commentId);
    }

    // 댓글 작성자 확인
    public boolean isCommentOwner(Long commentId, Long userId) {
        Long commentOwnerId = commentMapper.getCommentOwner(commentId);
        return commentOwnerId != null && commentOwnerId.equals(userId);
    }


    // 댓글 페이징 기능 구현
    public List<CommentDto> getCommentListWithPaging(Long reviewId, int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        return commentMapper.getCommentsByReviewIdWithPaging(reviewId, offset, pageSize);
    }

    public int getCommentCountByReviewId(Long reviewId) {
        return commentMapper.getCommentCountByReviewId(reviewId);
    }

}