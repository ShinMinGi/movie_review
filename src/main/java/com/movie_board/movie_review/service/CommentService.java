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
    public void addComment(CommentDto commentDto) {
        // userName이 null인지 확인
        if (commentDto.getUserName() == null) {
            throw new IllegalArgumentException("userName must not be null");
        }

        // parentId가 null이 아닌 경우 대댓글로 처리
        if (commentDto.getParentId() != null) {
            log.info("Reply to comment with ID: {}", commentDto.getParentId());
        }

        // CommentMapper를 사용하여 DB에 추가
        commentDto.setCreatedAt(LocalDateTime.now());
        commentMapper.insertComment(commentDto);
    }


    // 댓글 목록 조회
    public List<CommentDto> getCommentList(Long reviewId) {
        List<CommentDto> comments = commentMapper.selectCommentsByReviewId(reviewId);
        for (CommentDto comment : comments) {
            // 각 댓글에 대한 대댓글을 조회하여 추가
            List<CommentDto> replies = commentMapper.selectRepliesByParentId(comment.getId());
            comment.setReplies(replies);
        }
        return comments;
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
