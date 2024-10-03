package com.movie_board.movie_review.repository;

import com.movie_board.movie_review.controller.CommentController;
import com.movie_board.movie_review.dto.CommentDto;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentMapper {

    void insertComment(CommentDto commentDto);  // 댓글 삽입

    List<CommentDto> selectAllComments();  // 모든 댓글 조회

    void deleteComment(int id);  // 댓글 삭제
}
