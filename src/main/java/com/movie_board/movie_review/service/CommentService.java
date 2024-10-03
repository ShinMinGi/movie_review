package com.movie_board.movie_review.service;

import com.movie_board.movie_review.dto.CommentDto;
import com.movie_board.movie_review.repository.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentMapper commentMapper;




    public void addComment(CommentDto commentDto) {
        commentMapper.insertComment(commentDto);  // 댓글 삽입
    }

    public List<CommentDto> getComments() {
        return commentMapper.selectAllComments();  // 댓글 조회
    }

    public void deleteComment(int id) {
        commentMapper.deleteComment(id);  // 댓글 삭제
    }



}
