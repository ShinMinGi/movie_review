package com.movie_board.movie_review.repository;

import com.movie_board.movie_review.dto.CommentDto;
import com.movie_board.movie_review.dto.ReviewBoardDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {

    Long commentRegister(CommentDto commentDto);

    List<CommentDto> commentRead(int reviewId);

    void commentModify(CommentDto commentDto);

    void commentRemove(Long id); // 댓글 id로 삭제



    //----------------------------------------댓글 구현 start-------------------------------------------

    CommentDto commentFindById(int id);

    //----------------------------------------댓글 구현 end-------------------------------------------


}
