package com.movie_board.movie_review.repository;

import com.movie_board.movie_review.dto.CommentDto;
import com.movie_board.movie_review.dto.ReviewBoardDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper {


    // 댓글 등록
    void insertComment(CommentDto commentDto);


    // 댓글 목록 요청
    List<CommentDto> selectCommentsByReviewId(Long reviewId);

    // 특정 댓글의 대댓글 조회
    List<CommentDto> selectRepliesByParentId(@Param("parentId") Long parentId);

    // 댓글 수정
    void updateComment(Long commentId, String content, Long userId);

    // 댓글 삭제
    void deleteComment(Long commentId);

    // 댓글 작성자 조회
    Long getCommentOwner(Long commentId);

    //댓글 페이징 기능
    List<CommentDto> getCommentsByReviewIdWithPaging(@Param("reviewId") Long reviewId, @Param("offset") int offset, @Param("pageSize") int pageSize);

    int getCommentCountByReviewId(@Param("reviewId") Long reviewId);

    void insertReply(CommentDto commentDto);

}
