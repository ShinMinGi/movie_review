package com.movie_board.movie_review.repository;

import com.movie_board.movie_review.dto.PageDto;
import com.movie_board.movie_review.dto.ReviewBoardDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ReviewBoardMapper {
    List<ReviewBoardDto> findAllReviews();

    //게시글 클릭 시 조회
    ReviewBoardDto findById(Long id);

    // 등록
    void createReview(ReviewBoardDto reviewBoardDto);

    // 삭제
    void deleteReview(Long id);

    // 수정
    int updateReview(ReviewBoardDto reviewBoardDto);

    // 페이징된 리뷰 목록 조회
    List<ReviewBoardDto> selectPagedList(@Param("offset") int offset,
                                         @Param("limit") int limit,
                                         @Param("searchKeyword") String searchKeyword,
                                         @Param("filter") String filter);

    // 전체 게시글 수 조회
    int selectTotalCount();

    // 총 리뷰 수를 가져오는 메서드 추가 (-서칭)
    int countReviews(@Param("searchKeyword") String searchKeyword, @Param("filter") String filter);

    List<ReviewBoardDto> getReviewsByMovieId(Long movieId);
}