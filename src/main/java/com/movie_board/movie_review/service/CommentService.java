package com.movie_board.movie_review.service;

import com.movie_board.movie_review.dto.CommentDto;
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
    private final ReviewBoardService reviewBoardService;

    // 댓글 등록
    public void addComment(CommentDto commentDto) {
        // 여기서 userName이 null인지 확인
        if (commentDto.getUserName() == null) {
            throw new IllegalArgumentException("userName must not be null");
        }

        // CommentMapper를 사용하여 DB에 추가
        commentMapper.insertComment(commentDto);
    }


    // 댓글 목록 조회
    public List<CommentDto> getCommentList(Long reviewId) {
        return commentMapper.selectCommentsByReviewId(reviewId);
    }
}
