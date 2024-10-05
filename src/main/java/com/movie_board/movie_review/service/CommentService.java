package com.movie_board.movie_review.service;

import com.movie_board.movie_review.dto.CommentDto;
import com.movie_board.movie_review.dto.ReviewBoardDto;
import com.movie_board.movie_review.repository.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentMapper commentMapper;
    private final ReviewBoardService reviewBoardService;



    // 댓글 등록
    public Long registerComment(CommentDto commentDto) {
        commentMapper.commentRegister(commentDto);
        return commentDto.getId(); // 댓글 등록 후 ID 반환
    }

    // 리뷰 ID로 댓글 조회
    public List<CommentDto> getCommentsByReviewId(Long reviewId) {
        return commentMapper.getCommentsByReviewId(reviewId);
    }
}
