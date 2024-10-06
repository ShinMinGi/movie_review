package com.movie_board.movie_review.service;

import com.movie_board.movie_review.dto.CommentDto;
import com.movie_board.movie_review.dto.ReviewBoardDto;
import com.movie_board.movie_review.repository.CommentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class CommentService {

    private final CommentMapper commentMapper;
    private final ReviewBoardService reviewBoardService;



    // 댓글 등록
    public CommentDto registerComment(CommentDto commentDto) {
        log.info("Registering comment: {}", commentDto);
        commentMapper.commentRegister(commentDto);
        return commentDto; // 댓글 등록 후 ID 반환
    }

    // 리뷰 ID로 댓글 조회
    public List<CommentDto> getCommentsByReviewId(int reviewId, int movieId) {
        return commentMapper.getCommentsByReviewIdAndMovieId(reviewId, movieId);
    }
}
