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

    // 댓글 등록
    public Long registerComment(CommentDto commentDto) {
        commentMapper.commentRegister(commentDto);
        return commentMapper.commentRegister(commentDto);
    }// 등록된 댓글 ID 반환

    // 특정 리뷰의 댓글 조회
    public List<CommentDto> getCommentsByReviewId(int reviewId) {
        return commentMapper.commentRead(reviewId);
    }

    // 댓글 수정
    public void modifyComment(CommentDto commentDto) {
        commentMapper.commentModify(commentDto);
    }

    // 댓글 삭제
    public void removeComment(Long id) {
        commentMapper.commentRemove(id);
    }
    //----------------------------------------댓글 구현 start-------------------------------------------

    public CommentDto commentFindById(int id) {
        return commentMapper.commentFindById(id);
    }




    //----------------------------------------댓글 구현 end-------------------------------------------

}
